/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ui.tests.search;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.tests.model.AbstractDLTKSearchTests;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.ruby.ui.tests.internal.RubyUITestsPlugin;

public class MixinCompleteTests extends AbstractDLTKSearchTests {
	private static final String PROJECT_NAME = "mixins";

	public MixinCompleteTests(String name) {
		super(name);
	}

	public static Suite suite() {
		return new Suite(MixinCompleteTests.class);
	}

	@Override
	public void setUpSuite() throws Exception {
		super.setUpSuite();
		up();
		waitUntilIndexesReady();
		buildAll();
	}

	private void buildAll() throws CoreException {
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		// waitForAutoBuild();
	}

	@Override
	public void tearDownSuite() throws Exception {
		deleteProject(PROJECT_NAME);
		super.tearDownSuite();
	}

	private static String fgLastUsedID;

	protected String createUniqueId(IInterpreterInstallType InterpreterType) {
		String id = null;
		do {
			id = String.valueOf(System.currentTimeMillis());
		} while (InterpreterType.findInterpreterInstall(id) != null || id.equals(fgLastUsedID));
		fgLastUsedID = id;
		return id;
	}

	private void up() throws Exception {
		if (SCRIPT_PROJECT != null) {
			deleteProject(SCRIPT_PROJECT.getElementName());
		}
		SCRIPT_PROJECT = setUpScriptProject(PROJECT_NAME, RubyUITestsPlugin.PLUGIN_ID);
	}

	public void testMultiAccess() {

	}
}
