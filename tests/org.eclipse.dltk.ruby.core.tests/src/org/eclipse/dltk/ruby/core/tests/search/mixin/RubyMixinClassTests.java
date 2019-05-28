/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.search.mixin;

import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.ruby.core.tests.Activator;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinClass;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinModel;

public class RubyMixinClassTests extends AbstractModelTests {

	public RubyMixinClassTests(String name) {
		super(name);
	}

	public static Suite suite() {
		return new Suite(RubyMixinClassTests.class);
	}

	private static final String PROJECT_NAME = "mixin-includes";

	@Override
	public void setUpSuite() throws Exception {
		super.setUpSuite();
		setUpScriptProject(PROJECT_NAME, Activator.PLUGIN_ID);
		waitUntilIndexesReady();
	}

	@Override
	public void tearDownSuite() throws Exception {
		deleteProject(PROJECT_NAME);
		super.tearDownSuite();
	}

	public void testIncludeA() {
		RubyMixinModel m = RubyMixinModel.getWorkspaceInstance();
		RubyMixinClass a = (RubyMixinClass) m.createRubyElement("AAA%");
		RubyMixinClass[] includes = a.getIncluded();
		assertEquals(1, includes.length);
		assertEquals("BBB%", includes[0].getKey());
	}

	public void testIncludeB() {
		RubyMixinModel m = RubyMixinModel.getWorkspaceInstance();
		RubyMixinClass b = (RubyMixinClass) m.createRubyElement("BBB%");
		RubyMixinClass[] includes = b.getIncluded();
		assertEquals(1, includes.length);
		assertEquals("AAA%", includes[0].getKey());
	}

}
