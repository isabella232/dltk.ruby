/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.docs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterInstallChangedListener;
import org.eclipse.dltk.launching.PropertyChangeEvent;
import org.eclipse.dltk.launching.ScriptLaunchUtil;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ui.DLTKUIPlugin;

public class RiHelper {
	private static final String DOC_TERMINATION_LINE = "DLTKDOCEND"; //$NON-NLS-1$
	private static final long TERMINATE_WAIT_TIMEOUT = 2000;

	private static RiHelper instance;

	public static synchronized RiHelper getInstance() {
		if (instance == null) {
			instance = new RiHelper();
		}

		return instance;
	}

	private WeakHashMap<String, String> cache = new WeakHashMap<>();

	private Process riProcess;
	private OutputStreamWriter writer;
	private BufferedReader reader;
	private BufferedReader errorReader;

	private IDeployment deployment;

	protected static boolean isTerminated(Process riProcess) {
		try {
			riProcess.exitValue();
			return true;
		} catch (IllegalThreadStateException e) {
			return false;
		}
	}

	protected synchronized void runRiProcess()
			throws CoreException, IOException {
		IInterpreterInstall install = ScriptRuntime
				.getDefaultInterpreterInstall(RubyNature.NATURE_ID,
						LocalEnvironment.getInstance());

		if (install == null) {
			throw new CoreException(Status.CANCEL_STATUS);
		}

		IEnvironment env = install.getEnvironment();
		IExecutionEnvironment exeEnv = env
				.getAdapter(IExecutionEnvironment.class);
		deployment = exeEnv.createDeployment();
		if (deployment == null) {
			return;
		}
		IPath path = deployment.add(RubyUI.getDefault().getBundle(),
				"support/dltkri.rb"); //$NON-NLS-1$

		IFileHandle script = deployment.getFile(path);

		riProcess = ScriptLaunchUtil.runScriptWithInterpreter(exeEnv,
				install.getInstallLocation().toOSString(), script, null, null,
				null, install.getEnvironmentVariables());

		writer = new OutputStreamWriter(riProcess.getOutputStream());
		reader = new BufferedReader(
				new InputStreamReader(riProcess.getInputStream()));
		errorReader = new BufferedReader(
				new InputStreamReader(riProcess.getErrorStream()));
	}

	protected synchronized void destroyRiProcess() {
		if (writer != null) {
			closeQuietly(writer);
			writer = null;
		}
		if (reader != null) {
			closeQuietly(reader);
			reader = null;
		}
		if (errorReader != null) {
			closeQuietly(errorReader);
			errorReader = null;
		}
		if (riProcess != null) {
			try {
				final Watchdog watchdog = new Watchdog(TERMINATE_WAIT_TIMEOUT);
				watchdog.addListener(() -> riProcess.destroy());
				watchdog.start();
				try {
					riProcess.waitFor();
				} finally {
					watchdog.stop();
				}
			} catch (InterruptedException e) {
				// ignore
			}
			riProcess.destroy();
			riProcess = null;
		}
		if (deployment != null) {
			deployment.dispose();
			deployment = null;
		}
		// Cache should be cleared if we change interpreter
		cache.clear();
	}

	/**
	 * @param errorReader2
	 */
	private void closeQuietly(Reader r) {
		try {
			r.close();
		} catch (IOException e) {
			// ignore
		}
	}

	private static void closeQuietly(Writer w) {
		try {
			w.close();
		} catch (IOException e) {
			// ignore
		}
	}

	protected String readStderr() throws IOException {
		StringBuffer sb = new StringBuffer();
		String errorLine = null;
		while ((errorLine = errorReader.readLine()) != null) {
			sb.append(errorLine);
			sb.append("\n"); //$NON-NLS-1$
		}
		String error = sb.toString().trim();

		return error.length() > 0 ? error : null;
	}

	protected String readStdout() throws IOException {
		StringBuffer sb = new StringBuffer();
		do {
			String line = reader.readLine();
			if (line == null || line.equals(DOC_TERMINATION_LINE)) {
				break;
			}

			sb.append(line);
			sb.append('\n');
		} while (true);

		return sb.toString();
	}

	protected String loadRiDoc(String keyword) throws IOException {
		// Write
		writer.write(keyword + "\n"); //$NON-NLS-1$
		writer.flush();

		// Stderr
		// TODO: checking error!

		// Stdout
		return readStdout();
	}

	private boolean checkRiProcess() {
		if (riProcess == null || isTerminated(riProcess)) {
			try {
				runRiProcess();
			} catch (Exception e) {
				DLTKUIPlugin.logErrorMessage("Error starting RiHelper", e); //$NON-NLS-1$
				return false;
			}
		}

		return true;
	}

	protected RiHelper() {
		ScriptRuntime.addInterpreterInstallChangedListener(
				new IInterpreterInstallChangedListener() {
					@Override
					public void defaultInterpreterInstallChanged(
							IInterpreterInstall previous,
							IInterpreterInstall current) {
						destroyRiProcess();
					}

					@Override
					public void interpreterAdded(
							IInterpreterInstall Interpreter) {
					}

					@Override
					public void interpreterChanged(PropertyChangeEvent event) {
					}

					@Override
					public void interpreterRemoved(
							IInterpreterInstall Interpreter) {
					}
				});
		RubyUI.getDefault().addShutdownListener(() -> destroyRiProcess());
	}

	public synchronized String getDocFor(String keyword) {
		String doc = cache.get(keyword);

		if (doc == null) {
			if (checkRiProcess()) {
				try {
					doc = loadRiDoc(keyword);
					cache.put(keyword, doc);
				} catch (IOException e) {
					destroyRiProcess();
				}
			}
		}

		return doc;
	}
}
