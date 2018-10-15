/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model;


public class MethodArgument implements IMethodArgument {
	
	private String name;
	
	private MethodArgumentKind kind = MethodArgumentKind.SIMPLE;

	public MethodArgument(String name, MethodArgumentKind kind) {
		super();
		this.name = name;
		this.kind = kind;
	}

	@Override
	public MethodArgumentKind getKind() {
		return kind;
	}

	@Override
	public String getName() {
		return name;
	}			

}
