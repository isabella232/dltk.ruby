package org.eclipse.dltk.ruby.internal.debug.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.debug.ui.preferences.AbstractDebuggingOptionsBlock;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Ruby debug preference page
 */
public class RubyDebugPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static PreferenceKey BREAK_ON_FIRST_LINE = new PreferenceKey(
			RubyDebugPlugin.PLUGIN_ID,
			DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);

	private static PreferenceKey ENABLE_DBGP_LOGGING = new PreferenceKey(
			RubyDebugPlugin.PLUGIN_ID,
			DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);

	private static String PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug"; //$NON-NLS-1$
	private static String PROPERTY_PAGE_ID = "org.eclipse.dltk.ruby.propertyPage.debug"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractDebuggingOptionsBlock(newStatusChangedListener,
				project, getKeys(), container) {

			@Override
			protected PreferenceKey getBreakOnFirstLineKey() {
				return BREAK_ON_FIRST_LINE;
			}

			@Override
			protected PreferenceKey getDbgpLoggingEnabledKey() {
				return ENABLE_DBGP_LOGGING;
			}
		};
	}

	protected PreferenceKey[] getKeys() {
		return new PreferenceKey[] { BREAK_ON_FIRST_LINE, ENABLE_DBGP_LOGGING };
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
		setDescription(RubyDebugPreferencesMessages.RubyDebugPreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(RubyDebugPlugin.getDefault()
				.getPluginPreferences()));
	}
}
