/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.tests;

import java.util.Map;

import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;
import org.eclipse.dltk.ui.formatter.FormatterException;

public class CommentWrapTests extends AbstractRubyFormatterTest {

	public void testWrapping1() throws FormatterException {
		String input = joinLines("# 01234567890 01234567890", "def m1", "",
				"end");
		String output = joinLines("# 01234567890", "# 01234567890", "def m1",
				"", "end");
		assertEquals(output, format(input));
	}

	public void testWrapping2() throws FormatterException {
		String input = joinLines("# 01234567890 01234567890",
				"# 01234567890 01234567890", "def m1", "", "end");
		String output = joinLines("# 01234567890", "# 01234567890",
				"# 01234567890", "# 01234567890", "def m1", "", "end");
		assertEquals(output, format(input));
	}

	@Override
	protected Map<String, Object> getDefaultPreferences() {
		Map<String, Object> preferences = TestRubyFormatter
				.createTestingPreferences();
		preferences.put(RubyFormatterConstants.WRAP_COMMENTS,
				Boolean.TRUE.toString());
		preferences.put(RubyFormatterConstants.WRAP_COMMENTS_LENGTH, "20");
		return preferences;
	}

}
