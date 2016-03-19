/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.eclipse.dltk.ruby.basicdebugger;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.internal.launching.execution.DeploymentManager;
import org.eclipse.dltk.launching.DebuggingEngineRunner;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterConfig;
import org.eclipse.dltk.launching.debug.DbgpConnectionConfig;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;
import org.eclipse.dltk.ruby.internal.launching.JRubyInstallType;
import org.eclipse.dltk.ruby.launching.RubyLaunchConfigurationConstants;

public class RubyBasicDebuggerRunner extends DebuggingEngineRunner {
	public static final String ENGINE_ID = "org.eclipse.dltk.ruby.basicdebugger"; //$NON-NLS-1$

	private static final String RUBY_HOST_VAR = "DBGP_RUBY_HOST"; //$NON-NLS-1$
	private static final String RUBY_PORT_VAR = "DBGP_RUBY_PORT"; //$NON-NLS-1$
	private static final String RUBY_KEY_VAR = "DBGP_RUBY_KEY"; //$NON-NLS-1$
	private static final String RUBY_LOG_VAR = "DBGP_RUBY_LOG"; //$NON-NLS-1$

	private static final String DEBUGGER_SCRIPT = "BasicRunner.rb"; //$NON-NLS-1$

	protected IPath deploy(IDeployment deployment) throws CoreException {
		try {
			IPath deploymentPath = RubyBasicDebuggerPlugin.getDefault()
					.deployDebuggerSource(deployment);
			return deployment.getFile(deploymentPath).getPath();
		} catch (IOException e) {
			abort(
					Messages.RubyBasicDebuggerRunner_unableToDeployDebuggerSource,
					e);
		}

		return null;
	}

	public RubyBasicDebuggerRunner(IInterpreterInstall install) {
		super(install);
	}

	protected InterpreterConfig addEngineConfig(InterpreterConfig config,
			PreferencesLookupDelegate delegate, ILaunch launch)
			throws CoreException {
		IEnvironment env = getInstall().getEnvironment();
		IExecutionEnvironment exeEnv = env
				.getAdapter(IExecutionEnvironment.class);
		IDeployment deployment = exeEnv.createDeployment();
		if (deployment == null) {
			return null;
		}

		DeploymentManager.getInstance().addDeployment(launch, deployment);

		// Get debugger source location
		final IPath sourceLocation = deploy(deployment);

		final IPath scriptFile = sourceLocation.append(DEBUGGER_SCRIPT);

		// Creating new config
		InterpreterConfig newConfig = (InterpreterConfig) config.clone();

		if (getInstall().getInterpreterInstallType() instanceof JRubyInstallType) {
			newConfig.addEnvVar("JAVA_OPTS", "-Djruby.jit.enabled=false"); //$NON-NLS-1$ //$NON-NLS-2$
			//newConfig.addInterpreterArg("-C"); //$NON-NLS-1$
		}

		newConfig.addInterpreterArg("-r"); //$NON-NLS-1$
		newConfig.addInterpreterArg(env.convertPathToString(scriptFile));
		newConfig.addInterpreterArg("-I"); //$NON-NLS-1$
		newConfig.addInterpreterArg(env.convertPathToString(sourceLocation));

		// Environment
		DbgpConnectionConfig dbgpConfig = DbgpConnectionConfig.load(config);

		newConfig.addEnvVar(RUBY_HOST_VAR, dbgpConfig.getHost());
		newConfig.addEnvVar(RUBY_PORT_VAR, Integer.toString(dbgpConfig
				.getPort()));
		newConfig.addEnvVar(RUBY_KEY_VAR, dbgpConfig.getSessionId());

		String logFileName = getLogFileName(delegate, dbgpConfig.getSessionId());
		if (logFileName != null) {
			newConfig.addEnvVar(RUBY_LOG_VAR, logFileName);
		}

		return newConfig;
	}

	protected String getDebuggingEngineId() {
		return ENGINE_ID;
	}

	/*
	 * @see
	 * org.eclipse.dltk.launching.DebuggingEngineRunner#getDebugPreferenceQualifier
	 * ()
	 */
	protected String getDebugPreferenceQualifier() {
		return RubyDebugPlugin.PLUGIN_ID;
	}

	/*
	 * @seeorg.eclipse.dltk.launching.DebuggingEngineRunner#
	 * getDebuggingEnginePreferenceQualifier()
	 */
	protected String getDebuggingEnginePreferenceQualifier() {
		return RubyBasicDebuggerPlugin.PLUGIN_ID;
	}

	/*
	 * @seeorg.eclipse.dltk.launching.DebuggingEngineRunner#
	 * getLoggingEnabledPreferenceKey()
	 */
	protected String getLoggingEnabledPreferenceKey() {
		return RubyBasicDebuggerConstants.ENABLE_LOGGING;
	}

	/*
	 * @see
	 * org.eclipse.dltk.launching.DebuggingEngineRunner#getLogFileNamePreferenceKey
	 * ()
	 */
	protected String getLogFileNamePreferenceKey() {
		return RubyBasicDebuggerConstants.LOG_FILE_NAME;
	}

	/*
	 * @see
	 * org.eclipse.dltk.launching.DebuggingEngineRunner#getLogFilePathPreferenceKey
	 * ()
	 */
	protected String getLogFilePathPreferenceKey() {
		return RubyBasicDebuggerConstants.LOG_FILE_PATH;
	}

	protected String getProcessType() {
		return RubyLaunchConfigurationConstants.ID_RUBY_PROCESS_TYPE;
	}
}
