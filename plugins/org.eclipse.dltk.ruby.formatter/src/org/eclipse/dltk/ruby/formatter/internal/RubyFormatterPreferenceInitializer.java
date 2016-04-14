/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyFormatterPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = RubyFormatterPlugin.getDefault()
				.getPreferenceStore();
		//
		store.setDefault(RubyFormatterConstants.INDENT_CLASS, true);
		store.setDefault(RubyFormatterConstants.INDENT_MODULE, true);
		store.setDefault(RubyFormatterConstants.INDENT_METHOD, true);
		store.setDefault(RubyFormatterConstants.INDENT_BLOCKS, true);
		store.setDefault(RubyFormatterConstants.INDENT_CASE, false);
		store.setDefault(RubyFormatterConstants.INDENT_WHEN, true);
		store.setDefault(RubyFormatterConstants.INDENT_IF, true);
		//		
		store.setDefault(RubyFormatterConstants.LINES_FILE_AFTER_REQUIRE, 1);
		//
		store.setDefault(RubyFormatterConstants.LINES_FILE_BETWEEN_MODULE, 1);
		store.setDefault(RubyFormatterConstants.LINES_FILE_BETWEEN_CLASS, 1);
		store.setDefault(RubyFormatterConstants.LINES_FILE_BETWEEN_METHOD, 1);
		//
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_FIRST, 0);
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_MODULE, 1);
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_CLASS, 1);
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_METHOD, 1);
		//
		store.setDefault(RubyFormatterConstants.LINES_PRESERVE, 1);
		//
		store.setDefault(RubyFormatterConstants.WRAP_COMMENTS, false);
		store.setDefault(RubyFormatterConstants.WRAP_COMMENTS_LENGTH, 80);
	}
}
