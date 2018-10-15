/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parser;

import java.io.CharArrayReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.compiler.problem.AbstractProblemReporter;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.ruby.ast.FakeModuleDeclaration;
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;
import org.eclipse.dltk.ruby.ast.RubyModuleDeclaration;
import org.eclipse.dltk.ruby.core.utils.RubySyntaxUtils;
import org.eclipse.dltk.ruby.internal.parsers.jruby.DLTKRubyParser;
import org.eclipse.dltk.ruby.internal.parsers.jruby.RubyASTBuildVisitor;
import org.jruby.ast.Node;
import org.jruby.ast.visitor.NodeVisitor;
import org.jruby.parser.RubyParserResult;

public class JRubySourceParser extends AbstractSourceParser {

	private static boolean silentState = true;

	public static boolean isSilentState() {
		return silentState;
	}

	/**
	 * This option allows parser to suppress errors and exceptions and in result
	 * generate possibly partially non-correct AST instead of failing with
	 * exception. For running parser tests this option are being set to
	 * <code>false</code>.
	 */
	public static void setSilentState(boolean s) {
		silentState = s;
	}

	private static final boolean TRACE_AST_JRUBY = Boolean.valueOf(
			Platform.getDebugOption("org.eclipse.dltk.core/traceAST/jruby")) //$NON-NLS-1$
			.booleanValue();

	private static final boolean TRACE_AST_DLTK = Boolean.valueOf(
			Platform.getDebugOption("org.eclipse.dltk.core/traceAST/dltk")) //$NON-NLS-1$
			.booleanValue();

	private final boolean[] errorState = new boolean[1];

	private RubyParserResult parserResult;

	public RubyParserResult getParserResult() {
		return parserResult;
	}

	private class ProxyProblemReporter extends AbstractProblemReporter {

		private final IProblemReporter original;

		public ProxyProblemReporter(IProblemReporter original) {
			super();
			this.original = original;
		}

		@Override
		public void reportProblem(IProblem problem) {
			if (original != null)
				original.reportProblem(problem);
			if (problem.isError()) {
				errorState[0] = true;
			}
		}

	}

	public JRubySourceParser() {

	}

	/**
	 * Should return visitor for creating ModuleDeclaration from JRuby's AST
	 * 
	 * @param module
	 * @param content
	 * @return
	 */
	protected NodeVisitor getASTBuilderVisitor(ModuleDeclaration module,
			char[] content) {
		return new RubyASTBuildVisitor(module, content);
	}

	@Override
	public ModuleDeclaration parse(IModuleSource input,
			IProblemReporter reporter) {
		try {
			DLTKRubyParser parser = new DLTKRubyParser();
			ProxyProblemReporter proxyProblemReporter = new ProxyProblemReporter(
					reporter);
			errorState[0] = false;

			final long sTime = TRACE_AST_DLTK ? System.currentTimeMillis() : 0;
			final String fileName = input.getFileName() != null ? input
					.getFileName() : Util.EMPTY_STRING;
			char[] content = input.getContentsAsCharArray();
			char[] fixedContent = RubySpacedParensFixer
					.fixSpacedParens(content);
			Node node;
			if (Arrays.equals(fixedContent, content) != true) {
				// ssanders - Parse with reporter to collect parenthesis
				// warnings
				parser.parse(fileName, new CharArrayReader(content),
						proxyProblemReporter);
				// ssanders - However, use modified content to have corrected
				// position info
				node = parser.parse(fileName,
						new CharArrayReader(fixedContent), null);
			} else {
				node = parser.parse(fileName, new CharArrayReader(content),
						proxyProblemReporter);
			}
			final RubySourceFixer fixer = new RubySourceFixer();
			if (!parser.isSuccess() || errorState[0]) {
				String content2 = fixer.fix1(String.valueOf(fixedContent));

				Node node2 = parser.parse(fileName, new StringReader(content2),
						null);
				if (node2 != null)
					node = node2;
				else {
					fixer.clearPositions();

					content2 = fixer.fixUnsafe1(content2);

					node2 = parser.parse(fileName, new StringReader(content2),
							null);
					if (node2 != null)
						node = node2;
					else {
						fixer.clearPositions();

						content2 = fixer.fixUnsafe2(content2);

						node2 = parser.parse(fileName, new StringReader(
								content2), new AbstractProblemReporter() {

							@Override
							public void reportProblem(IProblem problem) {
								if (DLTKCore.DEBUG) {
									System.out
											.println("JRubySourceParser.parse(): Fallback Parse Problem - fileName=" + fileName + //$NON-NLS-1$
													", message=" //$NON-NLS-1$
													+ problem.getMessage()
													+ ", line=" + problem.getSourceLineNumber()); //$NON-NLS-1$
								}
							}

						});
					}
					if (node2 != null)
						node = node2;
					else
						fixer.clearPositions();
				}
				content = content2.toCharArray();
			}

			ModuleDeclaration module = new ModuleDeclaration(content.length);
			NodeVisitor visitor = getASTBuilderVisitor(module, content);
			if (node != null)
				node.accept(visitor);

			if (node != null) {
				if (TRACE_AST_JRUBY || TRACE_AST_DLTK)
					System.out.println("\n\nAST rebuilt\n"); //$NON-NLS-1$
				if (TRACE_AST_JRUBY)
					System.out.println("JRuby AST:\n" + node.toString()); //$NON-NLS-1$
				if (TRACE_AST_DLTK)
					System.out.println("DLTK AST:\n" + module.toString()); //$NON-NLS-1$
			}

			fixer.correctPositionsIfNeeded(module);

			if (TRACE_AST_DLTK) {
				long eTime = System.currentTimeMillis();
				System.out.println("Parsing took " + (eTime - sTime) //$NON-NLS-1$
						+ " ms"); //$NON-NLS-1$
			}
			this.parserResult = parser.getParserResult();

			if (!parser.isSuccess() && module.isEmpty()) {
				module = new FakeModuleDeclaration(content.length);
				minimumParse(content, module);
			}

			return module;
		} catch (Throwable t) {
			if (DLTKCore.DEBUG) {
				t.printStackTrace();
			}
			if (isSilentState()) {
				ModuleDeclaration mdl = new ModuleDeclaration(1);
				return mdl;
			}
			throw new RuntimeException(t);
		}
	}

	public ModuleDeclaration parse(String source) {
		return this.parse(new ModuleSource(source), null);
	}

	/**
	 * Really basic parse to find the first class or module definition, the
	 * intent is that a module declaration has at least a type in it (if one
	 * exists or can be parsed).
	 * 
	 * @param content
	 * @param md
	 */
	private static void minimumParse(char[] content, ModuleDeclaration md) {
		StringTokenizer toker = new StringTokenizer(new String(content));
		while (toker.hasMoreTokens()) {
			String token = toker.nextToken();
			if (token.equals("class") || token.equals("module")) { //$NON-NLS-1$ //$NON-NLS-2$
				String className = toker.nextToken();

				if (RubySyntaxUtils.isValidClass(className)) {
					String source = new String(content);
					// TODO(mhowe): Make position calculation more robust
					int indexOf = source.indexOf(className);
					int nameEnd = indexOf + className.length();
					RubyModuleDeclaration type;
					ASTNode nameNode = new ConstantReference(indexOf, nameEnd,
							className);
					Block bodyBlock = new Block(indexOf + nameEnd, source
							.length() - 1);
					if (token.equals("class")) { //$NON-NLS-1$
						type = new RubyClassDeclaration(null, nameNode,
								bodyBlock, indexOf, source.length() - 1);
					} else
						type = new RubyModuleDeclaration(nameNode, bodyBlock,
								indexOf, source.length() - 1);
					md.addStatement(type);
					if (toker.nextToken().equals("<")) { //$NON-NLS-1$
						String superClass = toker.nextToken();
						if (RubySyntaxUtils.isValidClass(superClass)) {
							indexOf = source.indexOf(className);
							type.addSuperClass(new ConstantReference(indexOf,
									indexOf + superClass.length(), superClass));
						}
					}
					return;
				}
			}
		}
	}

}
