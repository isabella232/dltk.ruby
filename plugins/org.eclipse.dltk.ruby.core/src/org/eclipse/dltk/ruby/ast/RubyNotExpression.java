/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ast;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class RubyNotExpression extends ASTNode {

	private final ASTNode value;

	public RubyNotExpression(int start, int end, ASTNode value) {
		super(start, end);
		this.value = value;
	}

	public ASTNode getValue() {
		return value;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (value != null)
				value.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
