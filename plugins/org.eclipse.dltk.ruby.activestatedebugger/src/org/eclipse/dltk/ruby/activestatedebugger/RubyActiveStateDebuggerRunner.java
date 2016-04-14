/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package org.eclipse.dltk.ruby.activestatedebugger;

import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.launching.ExternalDebuggingEngineRunner;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterConfig;
import org.eclipse.dltk.launching.debug.DbgpConnectionConfig;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;
import org.eclipse.dltk.ruby.launching.RubyLaunchConfigurationConstants;

/**
 * Debugging engine implementation for ActiveState's ruby debugging engine.
 * 
 * <p>
 * see: <a href=
 * "http://aspn.activestate.com/ASPN/docs/Komodo/komodo-doc-debugruby.html">
 * http://aspn.activestate.com/ASPN/docs/Komodo/komodo-doc-debugruby.html</a>
 * </p>
 */
public class RubyActiveStateDebuggerRunner extends
		ExternalDebuggingEngineRunner {

	public static final String ENGINE_ID = "org.eclipse.dltk.ruby.activestatedebugger"; //$NON-NLS-1$

	public RubyActiveStateDebuggerRunner(IInterpreterInstall install) {
		super(install);
	}

	@Override
	protected InterpreterConfig alterConfig(InterpreterConfig config,
			PreferencesLookupDelegate delegate) {

		IFileHandle debugEnginePath = getDebuggingEnginePath(delegate);
		DbgpConnectionConfig dbgpConfig = DbgpConnectionConfig.load(config);
		/*
		 * TODO: handle RUBYOPT support for rubygems
		 * 
		 * unset if not explicity set to a value by user? see:
		 * http://aspn.activestate
		 * .com/ASPN/docs/Komodo/komodo-doc-debugruby.html
		 */
		config
				.addEnvVar(
						"RUBYDB_OPTS", "RemotePort=" + dbgpConfig.getHost() + ":" + dbgpConfig.getPort()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		config.addEnvVar("DBGP_IDEKEY", dbgpConfig.getSessionId()); //$NON-NLS-1$
		// ruby -I"$dbgdir" -r "$dbgdir"/rdbgp.rb <Program_To_Debug.rb>
		config.addInterpreterArg("-I"); //$NON-NLS-1$
		config.addInterpreterArg(debugEnginePath.getParent().toString());

		config.addInterpreterArg("-r"); //$NON-NLS-1$
		config.addInterpreterArg(debugEnginePath.toOSString());

		return config;
	}

	@Override
	protected String getDebuggingEngineId() {
		return ENGINE_ID;
	}

	@Override
	protected String getDebuggingEnginePreferenceKey() {
		return RubyActiveStateDebuggerConstants.DEBUGGING_ENGINE_PATH_KEY;
	}

	@Override
	protected String getDebuggingEnginePreferenceQualifier() {
		return RubyActiveStateDebuggerPlugin.PLUGIN_ID;
	}

	@Override
	protected String getDebugPreferenceQualifier() {
		return RubyDebugPlugin.PLUGIN_ID;
	}

	protected String getLoggingEnabledPreferenceKey() {
		return RubyActiveStateDebuggerConstants.ENABLE_LOGGING;
	}

	@Override
	protected String getLogFileNamePreferenceKey() {
		return RubyActiveStateDebuggerConstants.LOG_FILE_NAME;
	}

	protected String getLogFilePathPreferenceKey() {
		return RubyActiveStateDebuggerConstants.LOG_FILE_PATH;
	}

	@Override
	protected String getProcessType() {
		return RubyLaunchConfigurationConstants.ID_RUBY_PROCESS_TYPE;
	}
}
