package org.eclipse.dltk.ruby.internal.debug.ui;

import org.eclipse.dltk.debug.ui.AbstractDebugUILanguageToolkit;
import org.eclipse.dltk.ruby.debug.RubyDebugConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyDebugUILanguageToolkit extends AbstractDebugUILanguageToolkit {

	@Override
	public String getDebugModelId() {
		return RubyDebugConstants.DEBUG_MODEL_ID;
	}
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		return RubyDebugUIPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public String[] getVariablesViewPreferencePages() {
		return new String[] { "org.eclipse.dltk.ruby.preferences.debug.detailFormatters" }; //$NON-NLS-1$
	}
}
