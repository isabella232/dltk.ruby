/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model;


public interface MethodArgumentKind {
	
	public class BlockArgument implements MethodArgumentKind {

	}

	public class VariableArgument implements MethodArgumentKind {

	}

	public class Simple implements MethodArgumentKind {
	}

	public static final MethodArgumentKind SIMPLE = new Simple();
	
	public static final MethodArgumentKind VARARG = new VariableArgument();
	
	public static final MethodArgumentKind BLOCK = new BlockArgument();

}
