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
package org.eclipse.dltk.ruby.formatter.lexer;

public class StringType {

	public static final StringType STRING = new StringType("STRING", false);

	public static final StringType HEREDOC_STRING = new StringType(
			"HEREDOC_STRING", true);

	public static final StringType XSTRING = new StringType("XSTRING", false);

	public static final StringType HEREDOC_XSTRING = new StringType(
			"HEREDOC_XSTRING", true);

	public static final StringType REGEXP = new StringType("REGEXP", false);

	private final String name;
	private final boolean heredoc;

	private StringType(String name, boolean heredoc) {
		this.name = name;
		this.heredoc = heredoc;
	}

	public boolean isHeredoc() {
		return heredoc;
	}

	public String toString() {
		return name;
	}

}
