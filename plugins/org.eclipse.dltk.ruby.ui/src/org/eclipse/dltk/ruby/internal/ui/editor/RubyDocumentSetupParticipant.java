/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ruby.internal.ui.text.IRubyPartitions;
import org.eclipse.dltk.ruby.internal.ui.text.RubyTextTools;
import org.eclipse.jface.text.IDocument;

/**
 * The document setup participant for Ruby.
 */
public class RubyDocumentSetupParticipant implements IDocumentSetupParticipant {

	public RubyDocumentSetupParticipant() {

	}

	@Override
	public void setup(IDocument document) {
		RubyTextTools tools = RubyUI.getDefault().getTextTools();
		tools.setupDocumentPartitioner(document,
				IRubyPartitions.RUBY_PARTITIONING);
	}
}
