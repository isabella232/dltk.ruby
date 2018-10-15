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

public class RubyEvaluatableStringExpression extends ASTNode {
	
	private final ASTNode body;
	
	public RubyEvaluatableStringExpression(int start, int end, ASTNode body) {
		super(start, end);
		this.body = body;
	}

	public ASTNode getBody() {
		return body;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (body != null)
				body.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
