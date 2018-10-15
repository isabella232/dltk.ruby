/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.ruby.core.tests.search;

import static org.eclipse.dltk.ruby.core.tests.Activator.search;
import static org.junit.Assert.assertEquals;

import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.tests.ProjectSetup;
import org.eclipse.dltk.core.tests.model.TestSearchResults;
import org.eclipse.dltk.ruby.core.tests.Activator;
import org.junit.ClassRule;
import org.junit.Test;

public class RubyMethodSearchTests implements IDLTKSearchConstants {

	@ClassRule
	public static final ProjectSetup PROJECT = new ProjectSetup(Activator.WORKSPACE, "search-methods");

	private final String methodName = "method001";

	@Test
	public void testDeclarations() throws Exception {
		final TestSearchResults results = search(methodName, METHOD, DECLARATIONS, PROJECT.getScriptProject());
		assertEquals(2, results.size());
		// results.assertType(className);
	}

	@Test
	public void testReferences() throws Exception {
		final TestSearchResults results = search(methodName, METHOD, REFERENCES, PROJECT.getScriptProject());
		assertEquals(2, results.size());
		// results.assertSourceModule(fileName);
	}

	@Test
	public void testAllOccurences() throws Exception {
		final TestSearchResults results = search(methodName, METHOD, ALL_OCCURRENCES, PROJECT.getScriptProject());
		assertEquals(4, results.size());
		// results.assertSourceModule(fileName);
		// results.assertType(className);
	}

}
