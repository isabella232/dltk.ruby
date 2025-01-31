/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.debug.ui.console;

import org.eclipse.osgi.util.NLS;

public class ConsoleMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.ruby.internal.debug.ui.console.ConsoleMessages";//$NON-NLS-1$

    public static String RubyFileHyperlink_Information_1;
    public static String RubyFileHyperlink_Error;
	public static String RubyFileHyperlink_Source_not_found_for__0__2;
	public static String RubyFileHyperlink_An_exception_occurred_while_following_link__3;
	public static String RubyFileHyperlink_Unable_to_parse_type_name_from_hyperlink__5;
	public static String RubyFileHyperlink_Unable_to_parse_line_number_from_hyperlink__6;
	public static String RubyFileHyperlink_Unable_to_parse_line_number_from_hyperlink__7;
	public static String RubyFileHyperlink_Unable_to_retrieve_hyperlink_text__8;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, ConsoleMessages.class);
	}

    public static String RubyFileHyperlink_0;

    public static String RubyFileHyperlink_1;
}
