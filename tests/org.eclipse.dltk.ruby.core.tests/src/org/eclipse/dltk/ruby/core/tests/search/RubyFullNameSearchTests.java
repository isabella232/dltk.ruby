/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.search;

import static org.eclipse.dltk.ruby.core.tests.Activator.search;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.tests.ProjectSetup;
import org.eclipse.dltk.core.tests.model.TestSearchResults;
import org.eclipse.dltk.ruby.core.tests.Activator;
import org.junit.ClassRule;
import org.junit.Test;

public class RubyFullNameSearchTests implements IDLTKSearchConstants {

	@ClassRule
	public static final ProjectSetup PROJECT = new ProjectSetup(Activator.WORKSPACE, "search");

	private final String shortTypeName = "Search002";
	private final String fullTypeName = "Module001::" + shortTypeName;
	private final String methodName = "method003";

	@Test
	public void testFullTypeNameSearch() throws Exception {
		final TestSearchResults results = search(fullTypeName, TYPE, DECLARATIONS, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertType(fullTypeName);
	}

	@Test
	public void testFullTypeReferenceSearch() throws Exception {
		final TestSearchResults results = search(fullTypeName, TYPE, REFERENCES, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertExists(ISourceModule.class, "Search001.rb");
	}

	@Test
	public void testSuperClassReferenceSearch1() throws Exception {
		final TestSearchResults results = search("Parent001", TYPE, REFERENCES, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertExists(IType.class, "Child001");
	}

	@Test
	public void testSuperClassReferenceSearch2() throws Exception {
		final TestSearchResults results = search("Parent002", TYPE, REFERENCES, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertExists(IType.class, "Child002");
	}

	@Test
	public void testShortTypeNameSearch() throws Exception {
		final TestSearchResults results = search(shortTypeName, TYPE, DECLARATIONS, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertType(fullTypeName);
	}

	@Test
	public void testShortMethodNameSearch() throws Exception {
		final TestSearchResults results = search(methodName, METHOD, DECLARATIONS, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertMethod(methodName);
		IMethod method = (IMethod) results.locate(IMethod.class, methodName);
		assertNotNull(method);
		assertNotNull(method.getParent());
		assertEquals(fullTypeName, ((IType) method.getParent()).getTypeQualifiedName("::"));
	}

	@Test
	public void testFullMethodNameSearch() throws Exception {
		final TestSearchResults results = search(fullTypeName + "::" + methodName, METHOD, DECLARATIONS,
				PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertMethod(methodName);
		IMethod method = (IMethod) results.locate(IMethod.class, methodName);
		assertNotNull(method);
		assertNotNull(method.getParent());
		assertEquals(fullTypeName, ((IType) method.getParent()).getTypeQualifiedName("::"));
	}

}
