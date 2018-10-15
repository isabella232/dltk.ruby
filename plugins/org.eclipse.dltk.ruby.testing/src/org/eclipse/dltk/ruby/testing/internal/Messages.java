/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.ruby.testing.internal.messages"; //$NON-NLS-1$
	public static String Delegate_errorExtractingRunner;
	public static String Delegate_internalErrorExtractingRunner;
	public static String Delegate_runnerNotFound;
	public static String openEditorError;
	public static String RubyTestingLaunchShortcut_selectConfigurationToDebug;
	public static String RubyTestingLaunchShortcut_selectConfigurationToRun;
	public static String RubyTestingLaunchShortcut_selectTestConfiguration;
	public static String RubyTestingLaunchShortcut_selectTestToDebug;
	public static String RubyTestingLaunchShortcut_selectTestToRun;
	public static String RubyTestingLaunchShortcut_testLaunch;
	public static String RubyTestingLaunchShortcut_testLaunchUnexpectedlyFailed;
	public static String RubyTestingLaunchShortcut_testSelection;
	public static String RubyTestingLaunchShortcut_theSelectedLaunchConfigurationDoesntHaveATestingEngineConfigured;
	public static String RubyTestingLaunchShortcut_unableToLocateAnyTestsInTheSpecifiedSelection;
	public static String RubyTestingMainLaunchConfigurationTab_rubyTestingEngine;
	public static String validate_notTestUnit;
	public static String validate_probablyTestUnit;
	public static String validate_notRSpec;
	public static String validate_probablyRSpec;
	public static String validate_notMinitest;
	public static String validate_probablyMinitest;
	public static String validate_runtimeError;
	public static String validate_sourceErrors;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
