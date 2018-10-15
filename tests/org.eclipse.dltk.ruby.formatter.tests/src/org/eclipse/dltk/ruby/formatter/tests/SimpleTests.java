/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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

import org.eclipse.dltk.ui.formatter.FormatterException;

public class SimpleTests extends AbstractRubyFormatterTest {

	public void test1() throws FormatterException {
		String input = joinLines("class Hello", "\t" + "attr_accessor :var",
				"end");
		String output = format(input);
		assertEquals(input, output);
	}

	public void test2() throws FormatterException {
		String input = joinLines("class Hello", "attr_accessor :var1",
				"attr_accessor :var2", "attr_accessor :var3", "end");
		String output = format(input);
		String expected = joinLines("class Hello", "\t" + "attr_accessor :var1",
				"\t" + "attr_accessor :var2", "\t" + "attr_accessor :var3",
				"end");
		assertEquals(expected, output);
	}

	public void test3() throws FormatterException {
		final String hw = "attr_accessor :var";
		final String tab2_hw = "\t\t\t\t" + hw;
		String input = joinLines("class Hello", tab2_hw, tab2_hw, tab2_hw,
				"end");
		String output = format(input);
		final String tab_hw = "\t" + hw;
		String expected = joinLines("class Hello", tab_hw, tab_hw, tab_hw,
				"end");
		assertEquals(expected, output);
	}

	public void test4() throws FormatterException {
		String input = joinLines("class Hello", "def execute",
				"puts \"Hello, world\"", "end", "end");
		String output = format(input);
		String expected = joinLines("class Hello", "\t" + "def execute",
				"\t\t" + "puts \"Hello, world\"", "\t" + "end", "end");
		assertEquals(expected, output);
	}

}
