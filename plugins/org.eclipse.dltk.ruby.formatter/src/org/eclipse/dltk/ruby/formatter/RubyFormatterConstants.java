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
package org.eclipse.dltk.ruby.formatter;

import org.eclipse.dltk.ui.CodeFormatterConstants;

public class RubyFormatterConstants {

	public static final String FORMATTER_TAB_CHAR = CodeFormatterConstants.FORMATTER_TAB_CHAR;
	public static final String FORMATTER_TAB_SIZE = CodeFormatterConstants.FORMATTER_TAB_SIZE;
	public static final String FORMATTER_INDENTATION_SIZE = CodeFormatterConstants.FORMATTER_INDENTATION_SIZE;

	public static final String FORMATTER_PROFILES = "formatter.profiles"; //$NON-NLS-1$
	public static final String FORMATTER_ACTIVE_PROFILE = "formatter.profiles.active"; //$NON-NLS-1$

	public static final String INDENT_CLASS = "indent.class"; //$NON-NLS-1$
	public static final String INDENT_MODULE = "indent.module"; //$NON-NLS-1$
	public static final String INDENT_METHOD = "indent.method"; //$NON-NLS-1$
	public static final String INDENT_BLOCKS = "indent.blocks"; //$NON-NLS-1$
	public static final String INDENT_IF = "indent.if"; //$NON-NLS-1$
	public static final String INDENT_CASE = "indent.case"; //$NON-NLS-1$
	public static final String INDENT_WHEN = "indent.when"; //$NON-NLS-1$

	public static final String LINES_FILE_AFTER_REQUIRE = "line.file.require.after"; //$NON-NLS-1$

	public static final String LINES_FILE_BETWEEN_MODULE = "line.file.module.between"; //$NON-NLS-1$
	public static final String LINES_FILE_BETWEEN_CLASS = "line.file.class.between"; //$NON-NLS-1$
	public static final String LINES_FILE_BETWEEN_METHOD = "line.file.method.between"; //$NON-NLS-1$

	public static final String LINES_BEFORE_FIRST = "line.first.before"; //$NON-NLS-1$
	public static final String LINES_BEFORE_MODULE = "line.module.before"; //$NON-NLS-1$
	public static final String LINES_BEFORE_CLASS = "line.class.before"; //$NON-NLS-1$
	public static final String LINES_BEFORE_METHOD = "line.method.before"; //$NON-NLS-1$

	public static final String LINES_PRESERVE = "lines.preserve"; //$NON-NLS-1$

	public static final String WRAP_COMMENTS = "wrap.comments"; //$NON-NLS-1$
	public static final String WRAP_COMMENTS_LENGTH = "wrap.comments.length"; //$NON-NLS-1$

}
