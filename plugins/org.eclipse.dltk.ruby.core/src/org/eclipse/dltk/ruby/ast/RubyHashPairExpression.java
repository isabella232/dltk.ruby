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

public class RubyHashPairExpression extends ASTNode {

	private final ASTNode key;
	private final ASTNode value;

	public RubyHashPairExpression(int start, int end, ASTNode key, ASTNode value) {
		super(start, end);
		this.key = key;
		this.value = value;
	}

	public ASTNode getKey() {
		return key;
	}

	public ASTNode getValue() {
		return value;
	}

	public int getKind() {
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (key != null)
				key.traverse(visitor);
			if (value != null)
				value.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
