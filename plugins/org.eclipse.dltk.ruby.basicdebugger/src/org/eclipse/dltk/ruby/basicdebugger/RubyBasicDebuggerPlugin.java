/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package org.eclipse.dltk.ruby.basicdebugger;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.ruby.abstractdebugger.AbstractRubyDebuggerPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RubyBasicDebuggerPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.ruby.basicdebugger"; //$NON-NLS-1$

	// The shared instance
	private static RubyBasicDebuggerPlugin plugin;
	
	/**
	 * The constructor
	 */
	public RubyBasicDebuggerPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
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
	public static RubyBasicDebuggerPlugin getDefault() {
		return plugin;
	}
	
	private static final String DEBUGGER_DIR = "debugger"; //$NON-NLS-1$

	public IPath deployDebuggerSource(IDeployment deployment) throws IOException {
		AbstractRubyDebuggerPlugin.getDefault().deployDebuggerSource(deployment);
		return deployment.add(getBundle(), DEBUGGER_DIR);
	}
}
