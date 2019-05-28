/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.internal.testing.model.TestCaseElement;
import org.eclipse.dltk.internal.testing.model.TestSuiteElement;
import org.eclipse.dltk.ruby.testing.internal.testunit.TestUnitTestRunnerUI;
import org.eclipse.dltk.ruby.testing.internal.testunit.TestUnitTestingEngine;
import org.eclipse.dltk.testing.ITestElementResolver;
import org.eclipse.dltk.testing.TestElementResolution;
import org.eclipse.dltk.testing.model.ITestElement;
import org.junit.AfterClass;
import org.junit.Test;

public class RubyTestingTestUnitResolverTests extends AbstractModelTests {

	/**
	 * @param testProjectName
	 * @param name
	 */
	public RubyTestingTestUnitResolverTests(String name) {
		super(name);
	}

	final static String projectName = "testing1"; //$NON-NLS-1$

	@Override
	public void setUpSuite() throws Exception {
		super.setUpSuite();
		setUpProject(projectName, AllTests.BUNDLE_ID);
		waitUntilIndexesReady();
	}

	@AfterClass
	public static void cleanUp() throws Exception {
		deleteProject(projectName);
	}

	private ITestElementResolver createResolver() {
		final TestUnitTestingEngine engine = new TestUnitTestingEngine();
		return new TestUnitTestRunnerUI(engine, getScriptProject(projectName));
	}

	@Test
	public void testResolveMath() throws ModelException {
		final ITestElementResolver resolver = createResolver();
		final String moduleName = "test_math.rb"; //$NON-NLS-1$
		final String className = "X::MathTest"; //$NON-NLS-1$
		final String methodName = "test1"; //$NON-NLS-1$
		//
		final String testName = methodName + '(' + className + ')';
		TestSuiteElement suiteElement = new TestSuiteElement(null, className, className, 1);
		ITestElement testElement = new TestCaseElement(suiteElement, testName, testName);
		final TestElementResolution resolution = resolver.resolveElement(testElement);
		assertNotNull(resolution);
		assertNotNull(resolution.getElement());
		assertEquals(IModelElement.METHOD, resolution.getElement().getElementType());
		assertEquals(methodName, resolution.getElement().getElementName());
		final ISourceRange range = resolution.getRange();
		assertNotNull(range);
		final IResource resource = resolution.getElement().getResource();
		assertEquals(moduleName, resource.getProjectRelativePath().toString());
		ISourceModule module = (ISourceModule) DLTKCore.create((IFile) resource);
		assertNotNull(module);
		final String source = module.getSource();
		final int offset = range.getOffset();
		assertEquals("def " + methodName, source.substring(offset, offset //$NON-NLS-1$
				+ range.getLength()));
		final TestElementResolution resolution1 = resolver.resolveElement(suiteElement);
		assertNotNull(resolution1);
		assertNotNull(resolution1.getElement());
		assertEquals(IModelElement.TYPE, resolution1.getElement().getElementType());
		assertEquals(className, ((IType) resolution1.getElement()).getFullyQualifiedName("::")); //$NON-NLS-1$
		final ISourceRange range1 = resolution1.getRange();
		assertNotNull(range1);
		final IResource resource1 = resolution1.getElement().getResource();
		assertEquals(moduleName, resource1.getProjectRelativePath().toString());
		ISourceModule module1 = (ISourceModule) DLTKCore.create((IFile) resource1);
		assertNotNull(module1);
		final String source1 = module1.getSource();
		final int offset1 = range1.getOffset();
		final String rangeContent = source1.substring(offset1, offset1 + range1.getLength());
		assertTrue(rangeContent.indexOf("class") >= 0); //$NON-NLS-1$
		assertTrue(rangeContent.indexOf(resolution1.getElement().getElementName()) >= 0);
	}
}
