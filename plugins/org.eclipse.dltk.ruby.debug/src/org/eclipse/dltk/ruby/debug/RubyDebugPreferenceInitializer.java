/*******************************************************************************
 * Copyright (c) 2008, 2016 Red Hat Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat Inc. - Use builtin debugger if ruby-debug is not installed
 *******************************************************************************/
package org.eclipse.dltk.ruby.debug;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;
import org.osgi.service.prefs.Preferences;

public class RubyDebugPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		Preferences store = DefaultScope.INSTANCE.getNode(RubyDebugPlugin.PLUGIN_ID);

		store.put(RubyDebugConstants.DEBUGGING_ENGINE_ID_KEY, "org.eclipse.dltk.ruby.basicdebugger"); //$NON-NLS-1$

		store.putBoolean(DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE, false);
		store.putBoolean(DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING, false);

		store.putBoolean(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_GLOBAL, true);
		store.putBoolean(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_CLASS, true);
		store.putBoolean(DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_LOCAL, true);
	}

}
