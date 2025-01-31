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
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class RubyDAssgnExpression extends RubyAssignment {

	private SimpleReference left;

	public RubyDAssgnExpression(int start, int end, String name, ASTNode value) {
		super(null, value);
		this.left = new VariableReference(start, start + name.length(), name);
		this.setStart(start);
		this.setEnd(end);
	}

	public String getName() {
		return left.getName();
	}

	public void setName(String name) {
		left.setName(name);
	}

	@Override
	public int getKind() {
		return 0;
	}

//	public void traverse(ASTVisitor visitor) throws Exception {
//		if (visitor.visit(this)) {	
//			if ()
//			visitor.endvisit(this);
//		}
//	}

	@Override
	public ASTNode getLeft() {
		return left;
	}

	
	
}
