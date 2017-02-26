/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.tests.IDLTKSearchConstantsForTests;
import org.eclipse.dltk.core.tests.WorkspaceSetup;
import org.eclipse.dltk.core.tests.model.TestSearchResults;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.ruby.core.tests";

	public static final WorkspaceSetup WORKSPACE = new WorkspaceSetup(Activator.PLUGIN_ID);

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static InputStream openResource(String path) throws IOException {
		URL url = getDefault().getBundle().getEntry(path);
		return new BufferedInputStream(url.openStream());
	}

	public static TestSearchResults search(String patternString, int searchFor, int limitTo, IScriptProject project)
			throws CoreException {
		final IDLTKSearchScope scope = SearchEngine.createSearchScope(project.getScriptProject());
		int matchRule = IDLTKSearchConstantsForTests.EXACT_RULE;
		if (patternString.indexOf('*') != -1 || patternString.indexOf('?') != -1) {
			matchRule |= SearchPattern.R_PATTERN_MATCH;
		}
		final IDLTKLanguageToolkit toolkit = scope.getLanguageToolkit();
		final SearchPattern pattern = SearchPattern.createPattern(patternString, searchFor, limitTo, matchRule,
				toolkit);
		assertNotNull("Pattern should not be null", pattern);
		final TestSearchResults results = new TestSearchResults();
		final SearchParticipant[] participants = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };
		new SearchEngine().search(pattern, participants, scope, results, null);
		return results;
	}
}
