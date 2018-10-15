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

public class RubyEnsureExpression extends ASTNode {

	private final ASTNode ensure;
	private final ASTNode body;



	public ASTNode getEnsure() {
		return ensure;
	}

	public ASTNode getBody() {
		return body;
	}

	public RubyEnsureExpression(int start, int end, ASTNode ensure, ASTNode body) {
		super(start, end);
		this.ensure = ensure;
		this.body = body;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (ensure != null)
				ensure.traverse(visitor);
			if (body != null)
				body.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
