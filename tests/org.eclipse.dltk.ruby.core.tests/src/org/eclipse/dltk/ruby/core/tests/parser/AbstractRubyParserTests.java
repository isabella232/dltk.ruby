/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.core.tests.Activator;

public abstract class AbstractRubyParserTests extends AbstractModelTests {
	public AbstractRubyParserTests(String testProjectName, String name) {
		super(testProjectName, name);
	}

	public ModuleDeclaration processScript(String name) throws Exception {
		ModuleDeclaration module = null;
		try (InputStream input = Activator.openResource(name);) {

			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			while (br.ready()) {
				String l = br.readLine();
				if (l != null) {
					buffer.append(l);
					buffer.append('\n');
				}
			}
			module = (ModuleDeclaration) DLTKLanguageManager.getSourceParser(
					RubyNature.NATURE_ID).parse(
					new ModuleSource(name, buffer.toString()), null);
			assertNotNull(module);
			assertFalse(module.isEmpty());
		}
		return module;
	}
}
