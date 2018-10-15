/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.parser.jruby;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.core.tests.Activator;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ParserSuite extends TestSuite {

	public ParserSuite(String testsDirectory) {
		super(testsDirectory);
		Enumeration<String> entryPaths = Activator.getDefault().getBundle()
				.getEntryPaths(testsDirectory);
		while (entryPaths.hasMoreElements()) {
			final String path = entryPaths.nextElement();
			if (path.endsWith(".exp"))
				continue;
			URL entry = Activator.getDefault().getBundle().getEntry(path);
			try {
				entry.openStream().close();
			} catch (Exception e) {
				continue;
			}
			int pos = path.lastIndexOf('/');
			final String name = (pos >= 0 ? path.substring(pos + 1) : path);
			pos = path.lastIndexOf('.');
			final String cleanPath = (pos >= 0 ? path.substring(0, pos) : path);
			addTest(new TestCase(name) {

				@Override
				public void setUp() {

				}

				@Override
				protected void runTest() throws Throwable {
					Map<Character, Integer> map = new HashMap<>();
					String input = loadInput(cleanPath + ".rb", map);
					String output = loadOutput(cleanPath + ".exp", map);

					ModuleDeclaration module = (ModuleDeclaration) DLTKLanguageManager
							.getSourceParser(RubyNature.NATURE_ID).parse(
									new ModuleSource(cleanPath + ".rb", input),
									null);
					assertNotNull(module);

					AST2StringVisitor vis = new AST2StringVisitor();
					module.traverse(vis);
					String fact = vis.getResult();

					if (!"success".equals(output.trim()))
						assertEquals(output, fact);
				}

			});
		}
	}

	public static String loadContent(String path) throws IOException {
		try (InputStream stream = Activator.openResource(path)) {
			int length = stream.available();
			byte[] data = new byte[length];
			stream.read(data);
			return new String(data, StandardCharsets.UTF_8);
		}
	}

	private String loadInput(String path, Map<Character, Integer> map) throws IOException {
		String content = loadContent(path);
		StringBuffer result = new StringBuffer();
		char[] charArray = content.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c > 'z') {
				map.put(new Character(c), new Integer(result.length()));
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

	private String loadOutput(String path, Map<Character, Integer> map) throws IOException {
		String content = loadContent(path);

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (c > '~') {
				Integer pos = map.get(new Character(c));
				Assert.isNotNull(pos);
				result.append(pos.intValue());
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

}
