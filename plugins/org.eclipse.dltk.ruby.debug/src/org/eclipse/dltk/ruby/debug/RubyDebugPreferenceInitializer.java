/*******************************************************************************
 * Copyright (c) 2008, 2016 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - Use builtin debugger if ruby-debug is not installed
 *******************************************************************************/
package org.eclipse.dltk.ruby.debug;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;

public class RubyDebugPreferenceInitializer extends
		AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		Preferences store = RubyDebugPlugin.getDefault().getPluginPreferences();

		if (store.getDefaultString(RubyDebugConstants.DEBUGGING_ENGINE_ID_KEY).isEmpty())
			store.setDefault(RubyDebugConstants.DEBUGGING_ENGINE_ID_KEY,
					"org.eclipse.dltk.ruby.basicdebugger"); //$NON-NLS-1$

		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE,
				false);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING,
				false);

		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_GLOBAL, true);
		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_CLASS, true);
		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_LOCAL, true);
	}

}
