/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.activestatedebugger.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.debug.ui.preferences.ExternalDebuggingEngineOptionsBlock;
import org.eclipse.dltk.ruby.activestatedebugger.RubyActiveStateDebuggerConstants;
import org.eclipse.dltk.ruby.activestatedebugger.RubyActiveStateDebuggerPlugin;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class RubyActiveStateDebuggerPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	static PreferenceKey ENGINE_PATH = new PreferenceKey(
			RubyActiveStateDebuggerPlugin.PLUGIN_ID,
			RubyActiveStateDebuggerConstants.DEBUGGING_ENGINE_PATH_KEY);

	static PreferenceKey ENABLE_LOGGING = new PreferenceKey(
			RubyActiveStateDebuggerPlugin.PLUGIN_ID,
			RubyActiveStateDebuggerConstants.ENABLE_LOGGING);

	static PreferenceKey LOG_FILE_PATH = new PreferenceKey(
			RubyActiveStateDebuggerPlugin.PLUGIN_ID,
			RubyActiveStateDebuggerConstants.LOG_FILE_PATH);

	static PreferenceKey LOG_FILE_NAME = new PreferenceKey(
			RubyActiveStateDebuggerPlugin.PLUGIN_ID,
			RubyActiveStateDebuggerConstants.LOG_FILE_NAME);

	private static final String PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug.engines.activestatedebugger"; //$NON-NLS-1$
	private static final String PROPERTY_PAGE_ID = "org.eclipse.dltk.ruby.propertyPage.debug.engines.activestatedebugger"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new ExternalDebuggingEngineOptionsBlock(
				newStatusChangedListener, project, new PreferenceKey[] {
						ENGINE_PATH, ENABLE_LOGGING, LOG_FILE_PATH,
						LOG_FILE_NAME }, container) {

			@Override
			protected void createLoggingBlock(Composite composite) {
				/*
				 * remove once the python active state debug runner supports
				 * setting up the log file
				 */
			}

			@Override
			protected PreferenceKey getDebuggingEnginePathKey() {
				return ENGINE_PATH;
			}

			@Override
			protected PreferenceKey getEnableLoggingPreferenceKey() {
				return ENABLE_LOGGING;
			}

			@Override
			protected PreferenceKey getLogFileNamePreferenceKey() {
				return LOG_FILE_NAME;
			}

			@Override
			protected PreferenceKey getLogFilePathPreferenceKey() {
				return LOG_FILE_PATH;
			}
		};
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getNatureId() {
		return RubyNature.NATURE_ID;
	}
	
	@Override
	protected String getProjectHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription(PreferenceMessages.PreferencesDescription);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(RubyActiveStateDebuggerPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}
}
