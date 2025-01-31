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

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class RubyParenthesisExpression extends ASTNode {

	private ASTListNode internals;

	public RubyParenthesisExpression(int start, int end,
			ASTListNode internals) {
		super(start, end);
		this.internals = internals;
	}

	public ASTListNode getInternals() {
		return internals;
	}

	public void setInternals(ASTListNode internals) {
		this.internals = internals;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (internals != null)
				internals.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
