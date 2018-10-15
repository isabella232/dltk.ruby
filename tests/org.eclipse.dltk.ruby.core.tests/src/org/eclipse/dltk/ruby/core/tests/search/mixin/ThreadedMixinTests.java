/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.search.mixin;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.tests.model.AbstractDLTKSearchTests;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.ruby.core.tests.Activator;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinModel;

public class ThreadedMixinTests extends AbstractDLTKSearchTests {
	private static final String PROJECT_NAME = "mixins";

	public ThreadedMixinTests(String name) {
		super(Activator.PLUGIN_ID, name);
	}

	public static Suite suite() {
		return new Suite(ThreadedMixinTests.class);
	}

	@Override
	public void setUpSuite() throws Exception {
		super.setUpSuite();
		up();
		waitUntilIndexesReady();
		buildAll();
	}

	private void buildAll() throws CoreException {
		ResourcesPlugin.getWorkspace()
				.build(IncrementalProjectBuilder.FULL_BUILD,
						new NullProgressMonitor());
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
		} while (InterpreterType.findInterpreterInstall(id) != null
				|| id.equals(fgLastUsedID));
		fgLastUsedID = id;
		return id;
	}

	private void up() throws Exception {
		if (SCRIPT_PROJECT != null) {
			deleteProject(SCRIPT_PROJECT.getElementName());
		}
		SCRIPT_PROJECT = setUpScriptProject(PROJECT_NAME);
	}

	class Access implements Runnable {
		private int start = 0;
		private int stop = 0;
		private int cycles = 0;
		private String[] keys;
		public boolean finish = false;
		private final RubyMixinModel mixinModel;

		public Access(RubyMixinModel mixinModel, String keys[], int start,
				int stop, int cycles) {
			this.mixinModel = mixinModel;
			this.start = start;
			this.stop = stop;
			this.cycles = cycles;
			this.keys = keys;
		}

		@Override
		public void run() {
			for (int i = 0; i < this.cycles; i++) {
				for (int j = 0; j < this.stop - this.start; j++) {
					mixinModel.createRubyElement(this.keys[this.start + j]);
					// System.out.println("#:" + this.start + ":" + this.stop);
				}
			}
			this.finish = true;
			System.out.println("Finished");
		}
	}

	public void testMultiAccess() throws Exception {
		int count = 10;
		final RubyMixinModel mixinModel = RubyMixinModel.getWorkspaceInstance();
		String[] findKeys = mixinModel.getRawModel().findKeys("*", new NullProgressMonitor());
		Thread[] threads = new Thread[count];
		Access[] access = new Access[count];
		int d = findKeys.length / count;
		for (int i = 0; i < count; i++) {
			if (i != count - 1) {
				Access a = new Access(mixinModel, findKeys, d * (i), d
						* (i + 1), 1);
				access[i] = a;
				threads[i] = new Thread(a);
			} else {
				Access a = new Access(mixinModel, findKeys, d * (i),
						findKeys.length, 10);
				access[i] = a;
				threads[i] = new Thread(a);
			}
		}
		long s = System.currentTimeMillis();
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join(100000);
			assertTrue(access[i].finish);
		}
		long e = System.currentTimeMillis();
		System.out.println("time:" + Long.toString(e - s));
	}
}
