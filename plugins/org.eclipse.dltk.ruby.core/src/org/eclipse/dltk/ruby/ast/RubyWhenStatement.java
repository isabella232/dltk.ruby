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

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.utils.CorePrinter;

public class RubyWhenStatement extends ASTNode {
	
	private List<ASTNode> expressions;
	private ASTNode body;
	
	public RubyWhenStatement(int start, int end) {
		super(start, end);
	}

	public List<ASTNode> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<ASTNode> expressions) {
		this.expressions = expressions;
	}

	public ASTNode getBody() {
		return body;
	}

	public void setBody(ASTNode body) {
		this.body = body;
	}

	public int getKind() {
		return 0;
	}

	@Override
	public void printNode(CorePrinter output) {		
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (expressions != null) {
				for (Iterator<ASTNode> iterator = expressions.iterator(); iterator
						.hasNext();) {
					ASTNode node = iterator.next();
					node.traverse(visitor);					
				}
			}
			if (body != null) {
				body.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

}
