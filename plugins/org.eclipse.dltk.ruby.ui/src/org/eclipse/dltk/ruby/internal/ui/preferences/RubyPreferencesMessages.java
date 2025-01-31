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
package org.eclipse.dltk.ruby.internal.ui.preferences;

import org.eclipse.osgi.util.NLS;

public final class RubyPreferencesMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.ruby.internal.ui.preferences.RubyPreferencesMessages"; //$NON-NLS-1$

	static {
		NLS.initializeMessages(BUNDLE_NAME, RubyPreferencesMessages.class);
	}

	private RubyPreferencesMessages() {
	}
	
	public static String EditorPreferencePageDescription;
	
	public static String EditorSyntaxColoringPreferencePageDescription;
	
	public static String EditorFoldingPreferencePageDescription;

	public static String GlobalPreferencePageDescription;

	public static String TodoTaskDescription;

}
