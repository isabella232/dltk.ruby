/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.typeinference;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ti.ITypeInferencer;

public class TypeInferenceTest extends AbstractTypeInferencingTests {

	private static final String SRC_PROJECT = "typeinference";

	public TypeInferenceTest(String name) {
		super("org.eclipse.dltk.ruby.core.tests", name);
	}

	@Override
	public void setUpSuite() throws Exception {
		PROJECT = setUpScriptProject(SRC_PROJECT);
		super.setUpSuite();
		waitUntilIndexesReady();
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		waitForAutoBuild();
	}
	
	@Override
	public void tearDownSuite() throws Exception {
		deleteProject(SRC_PROJECT);
		super.tearDownSuite();
	}

	public void executeTest(String folder, String name, ITypeInferencer inferencer, Collection<IAssertion> assertions)
			throws Exception {
		ISourceModule cu = getSourceModule(SRC_PROJECT, folder, name);
		ModuleDeclaration rootNode = RubyTypeInferencingUtils.parseSource(cu);
		for (Iterator<IAssertion> iter = assertions.iterator(); iter.hasNext();) {
			IAssertion assertion = iter.next();
			assertion.check(rootNode, cu, inferencer);
		}
	}

}
