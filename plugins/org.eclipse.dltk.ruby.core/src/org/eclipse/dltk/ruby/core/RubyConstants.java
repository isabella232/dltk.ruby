/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.ruby.core;

import org.eclipse.dltk.ast.Modifiers;

public class RubyConstants {
	protected RubyConstants() {

	}

	public final static String RUBY_PARTITIONING = "__ruby_partitioning"; //$NON-NLS-1$

	public static final int RubyAttributeModifier = 2 << (Modifiers.USER_MODIFIER + 1);

	public static final int RubyAliasModifier = 2 << (Modifiers.USER_MODIFIER + 2);

	public static final String REQUIRE = "require"; //$NON-NLS-1$
}
