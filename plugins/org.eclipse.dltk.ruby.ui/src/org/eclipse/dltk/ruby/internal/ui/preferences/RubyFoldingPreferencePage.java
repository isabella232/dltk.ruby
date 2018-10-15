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

import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ruby.internal.ui.text.folding.RubyDocFoldingPreferenceBlock;
import org.eclipse.dltk.ruby.internal.ui.text.folding.RubyFoldingPreferenceBlock;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.text.folding.DefaultFoldingPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.text.folding.IFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;

public class RubyFoldingPreferencePage extends
		AbstractConfigurationBlockPreferencePage {

	@Override
	protected IPreferenceConfigurationBlock createConfigurationBlock(
			OverlayPreferenceStore overlayPreferenceStore) {
		return new DefaultFoldingPreferenceConfigurationBlock(
				overlayPreferenceStore, this) {

			@Override
			protected IFoldingPreferenceBlock createDocumentationBlock(
					OverlayPreferenceStore store, PreferencePage page) {
				return new RubyDocFoldingPreferenceBlock(store, page);
			}

			@Override
			protected IFoldingPreferenceBlock createSourceCodeBlock(
					OverlayPreferenceStore store, PreferencePage page) {
				return new RubyFoldingPreferenceBlock(store, page);
			}
		};
	}

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		// setDescription(RubyPreferencesMessages.EditorFoldingPreferencePageDescription);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(RubyUI.getDefault().getPreferenceStore());
	}

}
