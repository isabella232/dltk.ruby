/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text;

import org.eclipse.dltk.ui.editor.highlighting.SemanticHighlighting;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.ui.texteditor.ITextEditor;

public class RubyTextTools extends ScriptTextTools {

	private final static String[] LEGAL_CONTENT_TYPES = new String[] {
			IRubyPartitions.RUBY_STRING,
			IRubyPartitions.RUBY_SINGLE_QUOTE_STRING,
			IRubyPartitions.RUBY_PERCENT_STRING, IRubyPartitions.RUBY_COMMENT,
			IRubyPartitions.RUBY_DOC };

	public RubyTextTools(boolean autoDisposeOnDisplayDispose) {
		super(IRubyPartitions.RUBY_PARTITIONING, LEGAL_CONTENT_TYPES,
				autoDisposeOnDisplayDispose);
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		return new RubySourceViewerConfiguration(getColorManager(),
				preferenceStore, editor, partitioning);
	}

	@Override
	public IPartitionTokenScanner getPartitionScanner() {
		return new RubyPartitionScanner();
	}

	@Override
	public SemanticHighlighting[] getSemanticHighlightings() {
		return new RubySemanticUpdateWorker().getSemanticHighlightings();
	}

}
