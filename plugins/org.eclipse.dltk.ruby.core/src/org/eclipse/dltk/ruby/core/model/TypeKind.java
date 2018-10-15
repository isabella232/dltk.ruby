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

public interface TypeKind {

	public class Unknown implements TypeKind {
	}

	public class Class implements TypeKind {
	}
	
	public class Builtin implements TypeKind {
	}

	public static final TypeKind UNKNOWN = new Unknown();

	public static final TypeKind CLASS = new Class();
	
	public static final TypeKind BUILTIN = new Builtin();

}
