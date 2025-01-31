/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IImportContainer;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ruby.core.RubyConstants;
import org.eclipse.dltk.ruby.core.RubyLanguageToolkit;
import org.eclipse.dltk.ruby.internal.ui.editor.RubyEditor;
import org.eclipse.dltk.ruby.internal.ui.preferences.SimpleRubySourceViewerConfiguration;
import org.eclipse.dltk.ruby.internal.ui.templates.RubyTemplateAccess;
import org.eclipse.dltk.ui.AbstractDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.templates.ITemplateAccess;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyUILanguageToolkit extends AbstractDLTKUILanguageToolkit {
	private static ScriptElementLabels sInstance = new ScriptElementLabels() {
		@Override
		public void getElementLabel(IModelElement element, long flags,
				StringBuffer buf) {
			StringBuffer buffer = new StringBuffer(60);
			super.getElementLabel(element, flags, buffer);
			String s = buffer.toString();
			if (s != null && !s.startsWith(element.getElementName())) {
				if (s.indexOf('$') != -1) {
					s = s.replaceAll("\\$", "."); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			buf.append(s);
		}

		@Override
		protected void getTypeLabel(IType type, long flags, StringBuffer buf) {
			StringBuffer buffer = new StringBuffer(60);
			super.getTypeLabel(type, flags, buffer);
			String s = buffer.toString();
			if (s.indexOf('$') != -1) {
				s = s.replaceAll("\\$", "::"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			buf.append(s);
		}

		@Override
		protected void getImportContainerLabel(IModelElement element,
				long flags, StringBuffer buf) {
			final IImportContainer container = (IImportContainer) element;
			if (RubyConstants.REQUIRE.equals(container.getContainerName())) {
				buf.append("requires");
			} else {
				super.getImportContainerLabel(element, flags, buf);
			}
		}
	};

	private static RubyUILanguageToolkit sToolkit = null;

	public static synchronized IDLTKUILanguageToolkit getInstance() {
		if (sToolkit == null) {
			sToolkit = new RubyUILanguageToolkit();
		}
		return sToolkit;
	}

	@Override
	public ScriptElementLabels getScriptElementLabels() {
		return sInstance;
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return RubyUI.getDefault().getPreferenceStore();
	}

	@Override
	public IDLTKLanguageToolkit getCoreToolkit() {
		return RubyLanguageToolkit.getDefault();
	}

	public IDialogSettings getDialogSettings() {
		return RubyUI.getDefault().getDialogSettings();
	}

	@Override
	public String getPartitioningId() {
		return RubyConstants.RUBY_PARTITIONING;
	}

	@Override
	public String getEditorId(Object inputElement) {
		return RubyEditor.EDITOR_ID;
	}

	@Override
	public String getInterpreterContainerId() {
		return "org.eclipse.dltk.ruby.launching.INTERPRETER_CONTAINER"; //$NON-NLS-1$
	}

	@Override
	public ScriptUILabelProvider createScriptUILabelProvider() {
		return null;
	}

	@Override
	public boolean getProvideMembers(ISourceModule element) {
		return true;
	}

	@Override
	public ScriptTextTools getTextTools() {
		return RubyUI.getDefault().getTextTools();
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguration() {
		return new SimpleRubySourceViewerConfiguration(getTextTools()
				.getColorManager(), getPreferenceStore(), null,
				getPartitioningId(), false);
	}

	private static final String INTERPRETERS_PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.interpreters"; //$NON-NLS-1$
	private static final String DEBUG_PREFERENCE_PAGE_ID = "org.eclipse.dltk.ruby.preferences.debug"; //$NON-NLS-1$
	private static final String[] EDITOR_PREFERENCE_PAGES_IDS = {
			"org.eclipse.dltk.ruby.preferences.editor", //$NON-NLS-1$
			"org.eclipse.dltk.ruby.preferences.editor.folding", //$NON-NLS-1$
			"org.eclipse.dltk.ruby.preferences.editor.syntaxcoloring", //$NON-NLS-1$ 
			"org.eclipse.dltk.ruby.preferences.templates" //$NON-NLS-1$
	};

	@Override
	public String getInterpreterPreferencePage() {
		return INTERPRETERS_PREFERENCE_PAGE_ID;
	}

	@Override
	public String getDebugPreferencePage() {
		return DEBUG_PREFERENCE_PAGE_ID;
	}

	@Override
	public String[] getEditorPreferencePages() {
		return EDITOR_PREFERENCE_PAGES_IDS;
	}

	@Override
	public ITemplateAccess getEditorTemplates() {
		return RubyTemplateAccess.getInstance();
	}
}
