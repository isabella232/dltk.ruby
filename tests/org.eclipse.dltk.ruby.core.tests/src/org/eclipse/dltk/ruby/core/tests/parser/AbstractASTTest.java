/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.parser;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.compiler.problem.AbstractProblemReporter;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.ruby.core.RubyNature;

public abstract class AbstractASTTest extends AbstractModelTests {

	protected static class CountingProblemReporter extends AbstractProblemReporter {

		public int count = 0;
		public String lastInfo = "<no errors>";

		public void reset() {
			count = 0;
		}

		public int getCount() {
			return count;
		}

		public String info() {
			return lastInfo;
		}

		@Override
		public void reportProblem(IProblem problem) {
			count++;
			lastInfo = problem.getMessage();
		}

	}

	protected final static CountingProblemReporter problems = new CountingProblemReporter();

	public AbstractASTTest(String testProjectName, String name) {
		super(name);
	}

	public AbstractASTTest(String name) {
		super(name);
	}

	protected ModuleDeclaration getAST(String content) {
		problems.reset();
		return (ModuleDeclaration) DLTKLanguageManager.getSourceParser(RubyNature.NATURE_ID)
				.parse(new ModuleSource(content), problems);
	}

	protected ASTNode getNodeAt(ASTNode root, final int start, final int end) throws Exception {
		assertTrue((start != -1) || (end != -1));
		final ASTNode[] result = new ASTNode[] { null };
		ASTVisitor visitor = new ASTVisitor() {

			@Override
			public boolean visitGeneral(ASTNode node) throws Exception {
				if ((node.sourceStart() == start || start == -1) && (node.sourceEnd() == end || end == -1)) {
					if (result[0] != null && !(result[0] instanceof ModuleDeclaration))
						throw new RuntimeException("Two different nodes on " + start + ":" + end);
					result[0] = node;
				}
				return true;
			}

		};

		root.traverse(visitor);

		return result[0];
	}

	protected abstract Class<?> getExpectedClass();

	protected ASTNode checkNode(String content, int start, int end) throws Exception {
		ModuleDeclaration ast = getAST(content);
		assertNotNull(ast);
		if (problems.getCount() != 0) {
			throw new RuntimeException(problems.info());
		}
		ASTNode nodeAt = getNodeAt(ast, start, end);
		assertNotNull(nodeAt);
		assertEquals(getExpectedClass(), nodeAt.getClass());
		return nodeAt;
	}

}
