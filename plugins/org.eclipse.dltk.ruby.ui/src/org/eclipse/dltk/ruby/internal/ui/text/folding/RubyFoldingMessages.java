/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.folding;

import org.eclipse.osgi.util.NLS;

public final class RubyFoldingMessages extends NLS {

	private static final String BUNDLE_NAME= "org.eclipse.dltk.ruby.internal.ui.text.folding.RubyFoldingMessages";//$NON-NLS-1$

	private RubyFoldingMessages() {
		// Do not instantiate
	}
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, RubyFoldingMessages.class);
	}
	
	public static String RubyFoldingPreferenceBlock_0;
	public static String RubyFoldingPreferenceBlock_10;
	public static String RubyFoldingPreferenceBlock_2;
	public static String RubyFoldingPreferenceBlock_3;
	public static String RubyFoldingPreferenceBlock_4;
	public static String RubyFoldingPreferenceBlock_6;
	
	public static String RubyFoldingPreferenceBlock_initRequires;
	public static String RubyFoldingPreferenceBlock_initFoldRDoc;
}
