/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.ruby.formatter.tests;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.ruby.formatter.RubyFormatter;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;
import org.eclipse.dltk.ui.CodeFormatterConstants;

public class TestRubyFormatter extends RubyFormatter {

	public TestRubyFormatter() {
		super(Util.LINE_SEPARATOR, createTestingPreferences());
	}

	public TestRubyFormatter(String lineDelimiter,
			Map<String, Object> preferences) {
		super(lineDelimiter, preferences);
	}

	@Override
	protected boolean isValidation() {
		return false;
	}

	public static Map<String, Object> createTestingPreferences() {
		final Map<String, Object> result = new HashMap<>();
		for (int i = 0; i < INDENTING.length; ++i) {
			result.put(INDENTING[i], Boolean.TRUE);
		}
		for (int i = 0; i < BLANK_LINES.length; ++i) {
			result.put(BLANK_LINES[i], new Integer(-1));
		}
		result.put(RubyFormatterConstants.FORMATTER_TAB_CHAR,
				CodeFormatterConstants.TAB);
		result.put(RubyFormatterConstants.FORMATTER_INDENTATION_SIZE,
				new Integer(1));
		result.put(RubyFormatterConstants.LINES_PRESERVE, new Integer(
				Integer.MAX_VALUE));
		return result;
	}

}
