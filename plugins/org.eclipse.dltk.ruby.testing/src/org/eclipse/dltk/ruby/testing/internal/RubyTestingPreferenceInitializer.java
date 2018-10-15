/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.testing.DLTKTestingPreferencesConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyTestingPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = RubyTestingPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(DLTKTestingPreferencesConstants.DO_FILTER_STACK, true);
	}

}
