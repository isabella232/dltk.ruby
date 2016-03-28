package org.eclipse.dltk.ruby.fastdebugger.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.fastdebugger.FastDebuggerPlugin;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class FastDebuggerPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	private static final String PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug.engines.railsdebugger"; //$NON-NLS-1$
	private static final String PROPERTY_PAGE_ID = "org.eclipse.dltk.ruby.propertyPage.debug.engines.railsdebugger"; //$NON-NLS-1$

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new FastDebuggerConfigurationBlock(newStatusChangedListener,
				project, container);
	}

	@Override
	protected String getHelpId() {
		return null;
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
	protected String getNatureId() {
		return RubyNature.NATURE_ID;
	}
	
	@Override
	protected void setDescription() {
		setDescription(FastDebuggerPreferenceMessages.PreferencesDescription);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(FastDebuggerPlugin.getDefault().getPreferenceStore());
	}
}
