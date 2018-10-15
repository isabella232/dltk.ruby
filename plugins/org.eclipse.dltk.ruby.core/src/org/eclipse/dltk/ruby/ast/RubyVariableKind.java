/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.ast;

import org.eclipse.dltk.ast.references.VariableKind;

public interface RubyVariableKind extends VariableKind {

	public class RubyImplementation extends Implementation implements
			RubyVariableKind {

		public RubyImplementation(VariableKind kind) {
			super(kind.getId());
		}
	}

	public static final RubyVariableKind LOCAL = new RubyImplementation(
			VariableKind.LOCAL);

	public static final RubyVariableKind GLOBAL = new RubyImplementation(
			VariableKind.GLOBAL);

	public static final RubyVariableKind INSTANCE = new RubyImplementation(
			VariableKind.INSTANCE);

	public static final RubyVariableKind CLASS = new RubyImplementation(
			VariableKind.CLASS);

	public static final RubyVariableKind CONSTANT = new RubyImplementation(
			VariableKind.GLOBAL);

}
