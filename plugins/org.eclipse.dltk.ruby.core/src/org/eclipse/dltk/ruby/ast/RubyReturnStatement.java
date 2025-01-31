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
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.utils.CorePrinter;

public class RubyReturnStatement extends ASTNode {

	
	private final CallArgumentsList value;
	private final int startOffset;
	private final int endOffset;

	public RubyReturnStatement(CallArgumentsList value, int startOffset, int endOffset) {
		this.value = value;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
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
			if (value != null) {
				value.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	public CallArgumentsList getValue() {
		return value;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

}
