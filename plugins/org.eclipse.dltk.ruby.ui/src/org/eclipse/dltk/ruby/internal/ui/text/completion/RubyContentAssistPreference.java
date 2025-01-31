/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.completion;

import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ContentAssistPreference;

public class RubyContentAssistPreference extends ContentAssistPreference {
	static RubyContentAssistPreference sDefault;
	@Override
	protected ScriptTextTools getTextTools() {
		return RubyUI.getDefault().getTextTools();
	}

	public static ContentAssistPreference getDefault() {
		if( sDefault == null ) {
			sDefault = new RubyContentAssistPreference();
		}
		return sDefault;
	}
}
