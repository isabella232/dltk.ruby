package org.eclipse.dltk.ruby.internal.debug.ui.launchConfigurations;

import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.debug.ui.launchConfigurations.RemoteLaunchConfigurationTab;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;

/**
 * 'Connect' launch configuration tab for remote ruby scripts
 */
public class RubyRemoteLaunchConfigurationTab extends
		RemoteLaunchConfigurationTab {

	public RubyRemoteLaunchConfigurationTab(String mode) {
		super(mode);
	}

	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(RubyDebugPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);
	}

	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(RubyDebugPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);
	}

	@Override
	protected String getNatureID() {
		return RubyNature.NATURE_ID;
	}

}
