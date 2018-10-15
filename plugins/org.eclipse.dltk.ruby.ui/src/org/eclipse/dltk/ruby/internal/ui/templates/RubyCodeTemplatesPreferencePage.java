/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.templates;

import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ruby.internal.ui.preferences.SimpleRubySourceViewerConfiguration;
import org.eclipse.dltk.ruby.internal.ui.text.IRubyPartitions;
import org.eclipse.dltk.ruby.internal.ui.text.RubyTextTools;
import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.ui.templates.ScriptTemplatePreferencePage;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;

import org.eclipse.jface.text.IDocument;

/**
 * Ruby code templates preference page
 */
public class RubyCodeTemplatesPreferencePage extends
		ScriptTemplatePreferencePage {
	@Override
	protected ScriptSourceViewerConfiguration createSourceViewerConfiguration() {
		return new SimpleRubySourceViewerConfiguration(getTextTools()
				.getColorManager(), getPreferenceStore(), null,
				IRubyPartitions.RUBY_PARTITIONING, false);
	}

	@Override
	protected ScriptTemplateAccess getTemplateAccess() {
		return RubyTemplateAccess.getInstance();
	}

	@Override
	protected void setDocumentPartitioner(IDocument document) {
		getTextTools().setupDocumentPartitioner(document,
				IRubyPartitions.RUBY_PARTITIONING);
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(RubyUI.getDefault().getPreferenceStore());
	}

	private RubyTextTools getTextTools() {
		return RubyUI.getDefault().getTextTools();
	}
}
