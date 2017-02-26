/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.debug.tests.console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ruby.debug.RubyFilenameLinenumberResolver;
import org.junit.BeforeClass;
import org.junit.Test;

public class RubyFilenameLinenumberTests {

	private static Pattern pattern;

	@BeforeClass
	public static void setupClass() {
		pattern = RubyFilenameLinenumberResolver.createPattern();
	}

	@Test
	public void testSimple() {
		assertTrue(pattern.matcher("script.rb:2").matches());
	}

	@Test
	public void testRoot() {
		assertTrue(pattern.matcher("/script.rb:2").matches());
	}

	@Test
	public void testFullWindows() {
		assertTrue(pattern.matcher("C:/runtime/A/script.rb:2").matches());
	}

	@Test
	public void testFullUnix() {
		assertTrue(pattern.matcher("/home/user/runtime/A/script.rb:2").matches());
	}

	@Test
	public void testFrom() {
		Matcher m = pattern.matcher("from c:/ruby/lib/ruby/site_ruby/1.8/rubygems.rb:61:in `require_gem'");
		assertTrue(m.find());
		assertEquals(2, m.groupCount());
		assertEquals("61", m.group(2));
		final String filename = m.group(1);
		assertTrue(filename.startsWith("c:/"));
		assertTrue(filename.endsWith("rubygems.rb"));
	}

	@Test
	public void testShoulda() {
		final String input = "c:/ruby/lib/ruby/gems/1.8/gems/Shoulda-1.2.0/lib/shoulda.rb:226:in `call'";
		Matcher m = pattern.matcher(input);
		assertTrue(m.find());
	}

	@Test
	public void testNegativeMatch() {
		// Matcher m = pattern
		// .matcher("C:0:Warning: require_gem is obsolete. Use gem instead.");
		// assertFalse(m.find());
	}

}
