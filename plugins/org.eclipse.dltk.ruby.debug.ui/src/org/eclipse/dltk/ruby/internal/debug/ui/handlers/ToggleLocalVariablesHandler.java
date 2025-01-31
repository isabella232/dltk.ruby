package org.eclipse.dltk.ruby.internal.debug.ui.handlers;

import org.eclipse.dltk.debug.ui.handlers.AbstractToggleLocalVariableHandler;
import org.eclipse.dltk.ruby.debug.RubyDebugConstants;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Toggles the display of Ruby local variables in the debug 'Variables' view
 */
public class ToggleLocalVariablesHandler extends
		AbstractToggleLocalVariableHandler {

	@Override
	protected String getModelId() {
		return RubyDebugConstants.DEBUG_MODEL_ID;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new PreferencesAdapter(RubyDebugPlugin.getDefault()
				.getPluginPreferences());
	}
}
