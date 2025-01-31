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

import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ti.goals.ItemReference;
import org.eclipse.dltk.ti.goals.PossiblePosition;

public class RubyMethodReference extends ItemReference {

	private CallExpression node;
	
	public RubyMethodReference(String name, String parentModelKey,
			PossiblePosition pos, int accuracy) {
		super(name, parentModelKey, pos, accuracy);
	}

	public CallExpression getNode() {
		return node;
	}

	public void setNode(CallExpression node) {
		this.node = node;
	}
	
}
