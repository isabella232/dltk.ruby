package org.eclipse.dltk.ruby.internal.debug.ui.preferences;

import org.eclipse.core.resources.IProject;

import org.eclipse.dltk.debug.ui.preferences.AbstractDebuggingEngineOptionsBlock;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.debug.RubyDebugConstants;
import org.eclipse.dltk.ruby.debug.RubyDebugPlugin;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;

import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Preference page for Ruby debugging engines
 */
public class RubyDebuggingEnginePreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	static PreferenceKey DEBUGGING_ENGINE = new PreferenceKey(
			RubyDebugPlugin.PLUGIN_ID,
			RubyDebugConstants.DEBUGGING_ENGINE_ID_KEY);

	private static final String PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug.engines"; //$NON-NLS-1$
	private static final String PROPERTY_PAGE_ID = "org.eclipse.dltk.ruby.propertyPage.debug.engines"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {

		return new AbstractDebuggingEngineOptionsBlock(
				newStatusChangedListener, project, getKeys(), container) {

			@Override
			protected String getNatureId() {
				return RubyNature.NATURE_ID;
			}

			@Override
			protected PreferenceKey getSavedContributionKey() {
				return DEBUGGING_ENGINE;
			}
		};
	}

	@Override
	protected String getHelpId() {
		// TODO Auto-generated method stub
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
		setDescription(RubyDebugPreferencesMessages.RubyDebugEnginePreferencePage_description);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(new PreferencesAdapter(RubyDebugPlugin.getDefault()
				.getPluginPreferences()));
	}

	private PreferenceKey[] getKeys() {
		return new PreferenceKey[] { DEBUGGING_ENGINE };
	}
}
