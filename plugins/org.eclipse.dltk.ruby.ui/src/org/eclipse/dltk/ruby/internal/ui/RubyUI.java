/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.dltk.core.IShutdownListener;
import org.eclipse.dltk.ruby.internal.ui.docs.RiHelper;
import org.eclipse.dltk.ruby.internal.ui.text.RubyTextTools;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RubyUI extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.ruby.ui"; //$NON-NLS-1$

	// The shared instance
	private static RubyUI plugin;

	private RubyTextTools fRubyTextTools;
	
	private ListenerList<IShutdownListener> shutdownListeners = new ListenerList<>();

	/**
	 * The constructor
	 */
	public RubyUI() {
		plugin = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// (new InitializeAfterLoadJob()).schedule();
	}
	
	public void addShutdownListener(IShutdownListener listener) {
		shutdownListeners.add(listener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (IShutdownListener listener : shutdownListeners) {
			listener.shutdown();
		}
		shutdownListeners.clear();
		plugin = null;
		super.stop(context);
	}

	public static RubyUI getDefault() {
		return plugin;
	}

	public RubyTextTools getTextTools() {
		if (fRubyTextTools == null) {
			fRubyTextTools = new RubyTextTools(true);
		}

		return fRubyTextTools;
	}

	public static void initializeAfterLoad(IProgressMonitor monitor)
			throws CoreException {
		RiHelper.getInstance().getDocFor("Object"); //$NON-NLS-1$
	}
}
