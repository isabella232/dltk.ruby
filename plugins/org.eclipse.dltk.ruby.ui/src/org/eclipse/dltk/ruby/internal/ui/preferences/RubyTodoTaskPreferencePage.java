/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.core.RubyPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.TodoTaskOptionsBlock;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class RubyTodoTaskPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription(RubyPreferencesMessages.TodoTaskDescription);
	}

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new TodoTaskOptionsBlock(newStatusChangedListener, project,
				container, RubyPlugin.PLUGIN_ID);
	}

	@Override
	protected String getNatureId() {
		return RubyNature.NATURE_ID;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(RubyPlugin.getDefault()
				.getPluginPreferences()));
	}

	@Override
	protected String getPreferencePageId() {
		return "org.eclipse.dltk.ruby.preferences.todo"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageId() {
		return "org.eclipse.dltk.ruby.propertyPage.todo"; //$NON-NLS-1$
	}

}
