/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.dltk.ruby.basicdebugger.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.debug.ui.preferences.DebuggingEngineConfigOptionsBlock;
import org.eclipse.dltk.ruby.basicdebugger.RubyBasicDebuggerConstants;
import org.eclipse.dltk.ruby.basicdebugger.RubyBasicDebuggerPlugin;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class RubyBasicDebuggerPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static PreferenceKey ENABLE_LOGGING = new PreferenceKey(
			RubyBasicDebuggerPlugin.PLUGIN_ID,
			RubyBasicDebuggerConstants.ENABLE_LOGGING);

	private static PreferenceKey LOG_FILE_PATH = new PreferenceKey(
			RubyBasicDebuggerPlugin.PLUGIN_ID,
			RubyBasicDebuggerConstants.LOG_FILE_PATH);

	private static PreferenceKey LOG_FILE_NAME = new PreferenceKey(
			RubyBasicDebuggerPlugin.PLUGIN_ID,
			RubyBasicDebuggerConstants.LOG_FILE_NAME);

	private static String PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug.engines.basicdebugger"; //$NON-NLS-1$
	private static String PROPERTY_PAGE_ID = "org.eclipse.dltk.ruby.propertyPage.debug.engines.basicdebugger"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new DebuggingEngineConfigOptionsBlock(newStatusChangedListener,
				project, new PreferenceKey[] { ENABLE_LOGGING, LOG_FILE_PATH,
						LOG_FILE_NAME }, container) {

			@Override
			protected void createEngineBlock(Composite composite) {
				// no engine preferences, yet...
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
		return null;
	}

	@Override
	protected String getNatureId() {
		return RubyNature.NATURE_ID;
	}
	
	@Override
	protected String getPreferencePageId() {
		return PREFERENCE_PAGE_ID;
	}

	@Override
	protected String getProjectHelpId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPropertyPageId() {
		return PROPERTY_PAGE_ID;
	}

	@Override
	protected void setDescription() {
		setDescription(PreferenceMessages.PreferencesDescription);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(RubyBasicDebuggerPlugin.getDefault()
				.getPreferenceStore());
	}
}
