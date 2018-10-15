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
package org.eclipse.dltk.ruby.ui.tests.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.dltk.ruby.internal.ui.text.hyperlink.RubyRequireHyperlinkDetector;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.junit.Test;

public class RubyRequireHyperlinkDetectorTest {

	/**
	 * @author Alexey
	 *
	 */
	private final class TestDetector extends RubyRequireHyperlinkDetector {

		public IHyperlink checkLine(String line) {
			return checkLine(0, line);
		}

		@Override
		protected IHyperlink createLink(int offset, String line, int begin,
				int end) {
			final String requiredFile = line.substring(begin, end);
			final Region region = new Region(offset + begin, end - begin);
			return new TestHyperlink(requiredFile, region);
		}
	}

	private static class TestHyperlink implements IHyperlink {

		private final String path;
		private final IRegion region;

		public TestHyperlink(String path, IRegion region) {
			this.path = path;
			this.region = region;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getHyperlinkText() {
			return path;
		}

		@Override
		public String getTypeLabel() {
			return null;
		}

		@Override
		public void open() {
			// empty
		}

	}

	private final TestDetector detector = new TestDetector();

	private void detect(String line, int offset, int length) {
		final IHyperlink hyperlink = detector.checkLine(line);
		assertNotNull(hyperlink);
		assertEquals(offset, hyperlink.getHyperlinkRegion().getOffset());
		assertEquals(length, hyperlink.getHyperlinkRegion().getLength());
		assertEquals(line.substring(offset, offset + length), hyperlink
				.getHyperlinkText());
	}

	@Test
	public void testSingleQuotes() {
		detect("require 'abc'", 9, 3);
	}

	@Test
	public void testDoubleQuotes() {
		detect("require \"abc\"", 9, 3);
	}

	@Test
	public void testManySpaces() {
		detect(" require  \"abc\"", 11, 3);
	}

	@Test
	public void testBracketsSingleQuotes() {
		detect("require('abc')", 9, 3);
	}

	@Test
	public void testBracketsDoubleQuotes() {
		detect("require(\"abc\")", 9, 3);
	}

	@Test
	public void testBracketsAndSpaces() {
		detect("require( 'abc' )", 10, 3);
	}

	@Test
	public void testNoHyperlink() {
		assertNull(detector.checkLine("require abc"));
		assertNull(detector.checkLine("require 'abc\""));
		assertNull(detector.checkLine("require \"abc'"));
		assertNull(detector.checkLine("require(abc)"));
		assertNull(detector.checkLine("require('abc\")"));
	}

}
