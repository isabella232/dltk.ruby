/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *     John Kaplan, johnkaplantech@gmail.com - 108071 [code templates] template for body of newly created class
 *******************************************************************************/
package org.eclipse.dltk.ruby.ui.preferences;

import org.eclipse.osgi.util.NLS;

public final class RubyPreferencesMessages extends NLS {

	private static final String BUNDLE_NAME= "org.eclipse.dltk.ruby.ui.preferences.RubyPreferencesMessages";//$NON-NLS-1$
	
	public static String RubyEditorPreferencePage_rubyDoc;
	public static String RubyEditorPreferencePage_rubyDocTopic;
	
	public static String RubyLocalVariable;
	public static String RubyClassVariable;
	public static String RubyInstanceVariable;
	public static String RubyGlobalVariable;
	public static String RubyPseudoVariable;
	public static String RubySymbols;
	public static String RubyConstants;
		
	private RubyPreferencesMessages() {
		// Do not instantiate
	}	
	static {
		NLS.initializeMessages(BUNDLE_NAME, RubyPreferencesMessages.class);
	}	
}
