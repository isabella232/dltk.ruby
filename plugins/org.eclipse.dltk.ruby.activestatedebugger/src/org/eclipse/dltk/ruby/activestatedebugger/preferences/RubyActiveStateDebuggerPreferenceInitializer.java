/*******************************************************************************
 * Copyright (c) 2016 Jae Gangemi and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jae Gangemi - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.activestatedebugger.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import org.eclipse.dltk.ruby.activestatedebugger.RubyActiveStateDebuggerConstants;
import org.eclipse.dltk.ruby.activestatedebugger.RubyActiveStateDebuggerPlugin;

import org.eclipse.jface.preference.IPreferenceStore;

public class RubyActiveStateDebuggerPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = RubyActiveStateDebuggerPlugin.getDefault()
				.getPreferenceStore();

		RubyActiveStateDebuggerConstants.initializeDefaults(store);
	}
}
