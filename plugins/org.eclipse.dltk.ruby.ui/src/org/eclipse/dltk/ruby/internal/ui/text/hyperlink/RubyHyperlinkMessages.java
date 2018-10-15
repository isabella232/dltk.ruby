/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.hyperlink;

import org.eclipse.osgi.util.NLS;

public class RubyHyperlinkMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.ruby.internal.ui.text.hyperlink.RubyHyperlinkMessages"; //$NON-NLS-1$
	public static String RequireHyperlink_BadSelection;
	public static String RequireHyperlink_message;
	public static String RequireHyperlink_title;
	public static String RequireHyperlink_text;
	public static String RequireHyperlink_label;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, RubyHyperlinkMessages.class);
	}

	private RubyHyperlinkMessages() {
	}
}
