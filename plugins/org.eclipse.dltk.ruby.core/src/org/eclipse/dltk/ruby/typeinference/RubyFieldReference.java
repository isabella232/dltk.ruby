/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ti.goals.ItemReference;
import org.eclipse.dltk.ti.goals.PossiblePosition;

public class RubyFieldReference extends ItemReference {

	private final ASTNode node;
	
	public RubyFieldReference(String name, String parentModelKey, PossiblePosition pos, ASTNode node) {
		super(name, parentModelKey, pos);
		this.node = node;
	}

	/**
	 * Node could be VariableReference or CallExpression or another value 
	 * changing node
	 */
	public ASTNode getNode() {
		return node;
	}
	
	

}
