/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.internal.testunit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.corext.SourceRange;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.core.utils.RubySyntaxUtils;
import org.eclipse.dltk.ruby.internal.debug.ui.console.RubyConsoleSourceModuleLookup;
import org.eclipse.dltk.ruby.testing.internal.AbstractRubyTestRunnerUI;
import org.eclipse.dltk.ruby.testing.internal.AbstractRubyTestingEngine;
import org.eclipse.dltk.ruby.testing.internal.AbstractTestingEngineValidateVisitor;
import org.eclipse.dltk.ruby.testing.internal.ResolverUtils;
import org.eclipse.dltk.ruby.testing.internal.RubyTestingPlugin;
import org.eclipse.dltk.testing.DLTKTestingMessages;
import org.eclipse.dltk.testing.TestElementResolution;
import org.eclipse.dltk.testing.model.ITestCaseElement;
import org.eclipse.dltk.testing.model.ITestSuiteElement;
import org.eclipse.osgi.util.NLS;

public class TestUnitTestRunnerUI extends AbstractRubyTestRunnerUI {

	private static final char CLASS_BEGIN = '(';
	private static final char CLASS_END = ')';

	/**
	 * @param testingEngine
	 */
	public TestUnitTestRunnerUI(AbstractRubyTestingEngine testingEngine,
			IScriptProject project) {
		super(testingEngine, project);
	}

	@Override
	public String getTestCaseLabel(ITestCaseElement caseElement, boolean full) {
		final String testName = caseElement.getTestName();
		int index = testName.lastIndexOf(CLASS_BEGIN);
		if (index > 0) {
			final int braceIndex = index;
			while (index > 0
					&& Character.isWhitespace(testName.charAt(index - 1))) {
				--index;
			}
			if (full) {
				int end = testName.length();
				if (end > braceIndex + 1
						&& testName.charAt(end - 1) == CLASS_END) {
					--end;
				}
				final String template = DLTKTestingMessages.TestSessionLabelProvider_testMethodName_className;
				return NLS.bind(template, testName.substring(braceIndex + 1,
						end), testName.substring(0, index));
			} else {
				return testName.substring(0, index);
			}
		} else {
			return testName;
		}
	}

	@Override
	public String getTestStartedMessage(ITestCaseElement caseElement) {
		final String testName = caseElement.getTestName();
		int index = testName.lastIndexOf(CLASS_BEGIN);
		if (index > 0) {
			int end = testName.length();
			if (end > index && testName.charAt(end - 1) == CLASS_END) {
				--end;
			}
			final String className = testName.substring(index + 1, end);
			while (index > 0
					&& Character.isWhitespace(testName.charAt(index - 1))) {
				--index;
			}
			final String method = testName.substring(0, index);
			return NLS.bind(
					DLTKTestingMessages.TestRunnerViewPart_message_started,
					className, method);
		} else {
			return testName;
		}
	}

	private static final String SHOULDA_TEST_PREFIX = "test:"; //$NON-NLS-1$

	@Override
	protected TestElementResolution resolveTestCase(ITestCaseElement testCase) {
		final String testName = testCase.getTestName();
		if (testName.length() == 0) {
			return null;
		}
		final int pos = testName.lastIndexOf(CLASS_BEGIN);
		if (!(pos > 0 && testName.charAt(testName.length() - 1) == CLASS_END)) {
			return null;
		}
		final String className = testName.substring(pos + 1,
				testName.length() - 1);
		if (!RubySyntaxUtils.isValidClass(className)) {
			return null;
		}
		final String methodName = testName.substring(0, pos).trim();
		if (RubySyntaxUtils.isRubyMethodName(methodName)) {
			final IMethod method = findMethod(className, methodName);
			if (method != null) {
				return new TestElementResolution(method, ResolverUtils
						.getSourceRange(method));
			}
		}
		final List types = findClasses(className);
		if (types == null) {
			return null;
		}
		if (methodName.startsWith(SHOULDA_TEST_PREFIX)) {
			String shouldName = methodName.substring(
					SHOULDA_TEST_PREFIX.length()).trim();
			if (shouldName.length() != 0
					&& shouldName.charAt(shouldName.length() - 1) == '.') {
				shouldName = shouldName.substring(0, shouldName.length() - 1)
						.trim();
			}
			if (shouldName.length() != 0) {
				final Set resources = new HashSet();
				for (Iterator i = types.iterator(); i.hasNext();) {
					final IType type = (IType) i.next();
					final IResource resource = type.getResource();
					if (resource != null && resource instanceof IFile) {
						resources.add(resource);
					}
				}
				if (resources.isEmpty()) {
					return null;
				}
				for (Iterator i = resources.iterator(); i.hasNext();) {
					final ISourceModule module = (ISourceModule) DLTKCore
							.create((IFile) i.next());
					final TestElementResolution resolution = findShould(module,
							className, shouldName);
					if (resolution != null) {
						return resolution;
					}
				}
			}
		}
		return null;
	}

	private static class ShouldLocator extends
			AbstractTestingEngineValidateVisitor {

		private static final String TWO_COLONS = "::"; //$NON-NLS-1$

		private final String className;
		private final String shouldName;

		private ISourceRange range = null;

		/**
		 * @param className
		 * @param shouldName
		 */
		public ShouldLocator(String className, String shouldName) {
			this.className = className;
			this.shouldName = shouldName;
		}

		final Stack typeMatches = new Stack();

		@Override
		public boolean visit(TypeDeclaration s) throws Exception {
			final String enclosingName = s.getEnclosingTypeName();
			final String fullName;
			if (enclosingName.length() == 0) {
				fullName = s.getName();
			} else {
				fullName = enclosingName.replaceAll("\\$", TWO_COLONS) + TWO_COLONS + s.getName(); //$NON-NLS-1$
			}
			typeMatches.push(Boolean.valueOf(className.equals(fullName)));
			return true;
		}

		@Override
		public boolean endvisit(TypeDeclaration s) throws Exception {
			typeMatches.pop();
			return true;
		}

		private boolean isMatchedType() {
			for (int i = 0, size = typeMatches.size(); i < size; ++i) {
				Boolean value = (Boolean) typeMatches.get(i);
				if (value.booleanValue()) {
					return true;
				}
			}
			return false;
		}

		final Stack calls = new Stack();

		@Override
		public boolean visitGeneral(ASTNode node) throws Exception {
			if (isMatchedType() && range == null) {
				if (node instanceof CallExpression) {
					final CallExpression call = (CallExpression) node;
					if (isMethodCall(call, ShouldaUtils.METHODS)
							&& call.getArgs().getChilds().size() >= 1) {
						final Object arg0 = call.getArgs().getChilds().get(0);
						if (arg0 instanceof RubyCallArgument) {
							final RubyCallArgument callArg = (RubyCallArgument) arg0;
							if (callArg.getValue() instanceof StringLiteral) {
								calls.push(call);
								if (isShouldMatched()) {
									range = new SourceRange(call.sourceStart(),
											callArg.sourceEnd()
													- call.sourceStart());
								}
							}
						}
					}
				}
			}
			return super.visitGeneral(node);
		}

		/**
		 * @return
		 */
		private boolean isShouldMatched() {
			if (isShouldMatched(shouldName)) {
				return true;
			}
			final String noTestClassName = className.replaceAll(
					"Test", Util.EMPTY_STRING); //$NON-NLS-1$
			if (startsWith(shouldName, noTestClassName)) {
				return isShouldMatched(shouldName.substring(
						noTestClassName.length()).trim());
			}
			return false;
		}

		private boolean startsWith(final String value, final String substring) {
			return value.length() > substring.length()
					&& value.startsWith(substring)
					&& Character.isWhitespace(value.charAt(substring.length()));
		}

		/**
		 * @param value
		 * @return
		 */
		private boolean isShouldMatched(String value) {
			for (int i = 0; i < calls.size(); ++i) {
				final CallExpression call = (CallExpression) calls.get(i);
				if (ShouldaUtils.SHOULD.equals(call.getName())) {
					if (!startsWith(value, ShouldaUtils.SHOULD)) {
						return false;
					}
					value = value.substring(ShouldaUtils.SHOULD.length())
							.trim();
					final RubyCallArgument callArg = (RubyCallArgument) call
							.getArgs().getChilds().get(0);
					final String literal = ((StringLiteral) callArg.getValue())
							.getValue();
					if (value.equals(literal)) {
						return true;
					}
				} else if (ShouldaUtils.CONTEXT.equals(call.getName())) {
					final RubyCallArgument callArg = (RubyCallArgument) call
							.getArgs().getChilds().get(0);
					final String literal = ((StringLiteral) callArg.getValue())
							.getValue().trim();
					if (!startsWith(value, literal)) {
						return false;
					}
					value = value.substring(literal.length()).trim();
				}
			}
			return false;
		}

		@Override
		public void endvisitGeneral(ASTNode node) throws Exception {
			if (!calls.isEmpty() && calls.peek() == node) {
				calls.pop();
			}
			super.endvisitGeneral(node);
		}
	}

	/**
	 * @param module
	 * @param className
	 * @param shouldName
	 * @return
	 */
	private TestElementResolution findShould(ISourceModule module,
			String className, String shouldName) {
		final ModuleDeclaration declaration = ResolverUtils.parse(module);
		if (declaration != null) {
			try {
				final ShouldLocator locator = new ShouldLocator(className,
						shouldName);
				declaration.traverse(locator);
				if (locator.range != null) {
					final ISourceRange range = ResolverUtils.adjustRange(module
							.getSource(), locator.range);
					return new TestElementResolution(module, range);
				}
			} catch (Exception e) {
				final String msg = "Error in findShould()"; //$NON-NLS-1$
				RubyTestingPlugin.error(msg, e);
			}
		}
		return null;
	}

	@Override
	protected TestElementResolution resolveTestSuite(ITestSuiteElement element) {
		final String className = element.getSuiteTypeName();
		if (RubySyntaxUtils.isValidClass(className)) {
			final List types = findClasses(className);
			if (types != null) {
				final IType type = (IType) types.get(0);
				return new TestElementResolution(type, ResolverUtils
						.getSourceRange(type));
			}
		}
		return null;
	}

	private static final class TypeSearchRequestor extends SearchRequestor {
		final List types = new ArrayList();

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			types.add(match.getElement());
		}
	}

	private static final class MethodRequestor extends SearchRequestor {
		IMethod method = null;

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			method = (IMethod) match.getElement();
		}
	}

	/**
	 * @param className
	 * @param methodName
	 * @return
	 */
	private IMethod findMethod(String className, String methodName) {
		final IDLTKSearchScope scope = getSearchScope();
		final String sPattern = className + "::" + methodName; //$NON-NLS-1$
		SearchPattern pattern = SearchPattern.createPattern(sPattern,
				IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS,
				SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE,
				scope.getLanguageToolkit());
		try {
			final MethodRequestor requestor = new MethodRequestor();
			new SearchEngine().search(pattern,
					new SearchParticipant[] { SearchEngine
							.getDefaultSearchParticipant() }, scope, requestor,
					null);
			return requestor.method;
		} catch (CoreException e) {
			final String msg = "Error in findMethod({0}::{1})"; //$NON-NLS-1$
			RubyTestingPlugin.error(NLS.bind(msg, className, methodName), e);
		}
		return null;
	}

	/**
	 * @param className
	 */
	private List findClasses(String className) {
		final IDLTKSearchScope scope = getSearchScope();
		SearchPattern pattern = SearchPattern.createPattern(className,
				IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS,
				SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE,
				scope.getLanguageToolkit());
		try {
			final TypeSearchRequestor requestor = new TypeSearchRequestor();
			new SearchEngine().search(pattern,
					new SearchParticipant[] { SearchEngine
							.getDefaultSearchParticipant() }, scope, requestor,
					null);
			if (!requestor.types.isEmpty()) {
				return requestor.types;
			}
		} catch (CoreException e) {
			final String msg = "Error in findClasses({0})"; //$NON-NLS-1$
			RubyTestingPlugin.error(NLS.bind(msg, className), e);
		}
		return null;
	}

	private static final String[] TEST_UNIT = { "test", "unit" }; //$NON-NLS-1$ //$NON-NLS-2$

	private boolean testFragmentPath(IPath fragmentPath, IPath path) {
		if (pathEquality.isPrefixOf(fragmentPath, path)
				&& path.segmentCount() > fragmentPath.segmentCount()
						+ TEST_UNIT.length) {
			for (int j = 0; j < TEST_UNIT.length; ++j) {
				if (!TEST_UNIT[j].equals(path.segment(fragmentPath
						.segmentCount()
						+ j))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private static String buildRegex() {
		final String slash = "[\\\\/]"; //$NON-NLS-1$
		return slash + "gems" + slash + "Shoulda-[\\w\\.]+" + slash + "lib" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ slash;
	}

	private static final Pattern GEM_SHOULDA_LIB = Pattern
			.compile(buildRegex());

	@Override
	protected boolean selectLine(String line) {
		final String filename = extractFileName(line);
		if (filename == null) {
			return true;
		}
		if (filename.endsWith(TestUnitTestingEngine.TEST_UNIT_RUNNER)) {
			return false;
		}
		if (GEM_SHOULDA_LIB.matcher(filename).find()) {
			return false;
		}
		final IPath path = new Path(filename);
		try {
			final IProjectFragment[] fragments = project.getProjectFragments();
			for (int i = 0; i < fragments.length; ++i) {
				final IProjectFragment fragment = fragments[i];
				if (fragment.isExternal()
						&& testFragmentPath(EnvironmentPathUtils
								.getLocalPath(fragment.getPath()), path)
						&& RubyConsoleSourceModuleLookup.isIncluded(fragment,
								path)) {
					return false;
				}
			}
		} catch (ModelException e) {
			return true;
		}
		return true;
	}
}
