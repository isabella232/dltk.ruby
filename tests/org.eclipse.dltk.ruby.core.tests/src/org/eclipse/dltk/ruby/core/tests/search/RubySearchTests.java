/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *     xored software, Inc. - Search All occurences bugfix,
 *     						  hilight only class name when class is in search results ( Alex Panchenko <alex@xored.com>)
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

public class RubySearchTests implements IDLTKSearchConstants {

	@ClassRule
	public static final ProjectSetup PROJECT = new ProjectSetup(Activator.WORKSPACE, "search");

	private final String fileName = "Search001.rb";
	private final String className = "Search001";

	@Test
	public void testDeclarations() throws Exception {
		final TestSearchResults results = search(className, TYPE, DECLARATIONS, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertType(className);
	}

	@Test
	public void testReferences() throws Exception {
		final TestSearchResults results = search(className, TYPE, REFERENCES, PROJECT.getScriptProject());
		assertEquals(1, results.size());
		results.assertSourceModule(fileName);
	}

	@Test
	public void testAllOccurences() throws Exception {
		final TestSearchResults results = search(className, TYPE, ALL_OCCURRENCES, PROJECT.getScriptProject());
		assertEquals(2, results.size());
		results.assertSourceModule(fileName);
		results.assertType(className);
	}

}
