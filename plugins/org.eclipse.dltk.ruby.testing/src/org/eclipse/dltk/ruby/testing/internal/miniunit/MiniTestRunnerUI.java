/*******************************************************************************
 * Copyright (c) 2016, 2017 Red Hat, Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and Implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.internal.miniunit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
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
import org.eclipse.dltk.ruby.core.utils.RubySyntaxUtils;
import org.eclipse.dltk.ruby.internal.debug.ui.console.RubyConsoleSourceModuleLookup;
import org.eclipse.dltk.ruby.testing.internal.AbstractRubyTestRunnerUI;
import org.eclipse.dltk.ruby.testing.internal.AbstractRubyTestingEngine;
import org.eclipse.dltk.ruby.testing.internal.ResolverUtils;
import org.eclipse.dltk.ruby.testing.internal.RubyTestingPlugin;
import org.eclipse.dltk.testing.DLTKTestingMessages;
import org.eclipse.dltk.testing.TestElementResolution;
import org.eclipse.dltk.testing.model.ITestCaseElement;
import org.eclipse.dltk.testing.model.ITestSuiteElement;
import org.eclipse.osgi.util.NLS;

public class MiniTestRunnerUI extends AbstractRubyTestRunnerUI {

	private static final char CLASS_BEGIN = '(';
	private static final char CLASS_END = ')';

	/**
	 * @param testingEngine
	 */
	public MiniTestRunnerUI(AbstractRubyTestingEngine testingEngine, IScriptProject project) {
		super(testingEngine, project);
	}

	@Override
	public String getTestCaseLabel(ITestCaseElement caseElement, boolean full) {
		final String testName = caseElement.getTestName();
		int index = testName.lastIndexOf(CLASS_BEGIN);
		if (index > 0) {
			final int braceIndex = index;
			while (index > 0 && Character.isWhitespace(testName.charAt(index - 1))) {
				--index;
			}
			if (full) {
				int end = testName.length();
				if (end > braceIndex + 1 && testName.charAt(end - 1) == CLASS_END) {
					--end;
				}
				final String template = DLTKTestingMessages.TestSessionLabelProvider_testMethodName_className;
				return NLS.bind(template, testName.substring(braceIndex + 1, end), testName.substring(0, index));
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
			while (index > 0 && Character.isWhitespace(testName.charAt(index - 1))) {
				--index;
			}
			final String method = testName.substring(0, index);
			return NLS.bind(DLTKTestingMessages.TestRunnerViewPart_message_started, className, method);
		} else {
			return testName;
		}
	}

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
		final String className = testName.substring(pos + 1, testName.length() - 1);
		if (!RubySyntaxUtils.isValidClass(className)) {
			return null;
		}
		final String methodName = testName.substring(0, pos).trim();
		if (RubySyntaxUtils.isRubyMethodName(methodName)) {
			final IMethod method = findMethod(className, methodName);
			if (method != null) {
				return new TestElementResolution(method, ResolverUtils.getSourceRange(method));
			}
		}
		final List<IType> types = findClasses(className);
		if (types == null) {
			return null;
		}
		return null;
	}

	@Override
	protected TestElementResolution resolveTestSuite(ITestSuiteElement element) {
		final String className = element.getSuiteTypeName();
		if (RubySyntaxUtils.isValidClass(className)) {
			final List<IType> types = findClasses(className);
			if (types != null) {
				final IType type = types.get(0);
				return new TestElementResolution(type, ResolverUtils.getSourceRange(type));
			}
		}
		return null;
	}

	private static final class TypeSearchRequestor extends SearchRequestor {
		final List<IType> types = new ArrayList<>();

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			types.add((IType) match.getElement());
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
		SearchPattern pattern = SearchPattern.createPattern(sPattern, IDLTKSearchConstants.METHOD,
				IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE,
				scope.getLanguageToolkit());
		try {
			final MethodRequestor requestor = new MethodRequestor();
			new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() },
					scope, requestor, null);
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
	private List<IType> findClasses(String className) {
		final IDLTKSearchScope scope = getSearchScope();
		SearchPattern pattern = SearchPattern.createPattern(className, IDLTKSearchConstants.TYPE,
				IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE,
				scope.getLanguageToolkit());
		try {
			final TypeSearchRequestor requestor = new TypeSearchRequestor();
			new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() },
					scope, requestor, null);
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
				&& path.segmentCount() > fragmentPath.segmentCount() + TEST_UNIT.length) {
			for (int j = 0; j < TEST_UNIT.length; ++j) {
				if (!TEST_UNIT[j].equals(path.segment(fragmentPath.segmentCount() + j))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	protected boolean selectLine(String line) {
		final String filename = extractFileName(line);
		if (filename == null) {
			return true;
		}
		if (filename.endsWith(MiniTestingEngine.MINITEST_RUNNER)) {
			return false;
		}
		final IPath path = new Path(filename);
		try {
			final IProjectFragment[] fragments = project.getProjectFragments();
			for (int i = 0; i < fragments.length; ++i) {
				final IProjectFragment fragment = fragments[i];
				if (fragment.isExternal()
						&& testFragmentPath(EnvironmentPathUtils.getLocalPath(fragment.getPath()), path)
						&& RubyConsoleSourceModuleLookup.isIncluded(fragment, path)) {
					return false;
				}
			}
		} catch (ModelException e) {
			return true;
		}
		return true;
	}
}
