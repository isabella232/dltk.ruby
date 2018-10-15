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
import org.eclipse.dltk.utils.CorePrinter;

public class RubyCallArgument extends ASTNode {

	public final static int SIMPLE = 0;

	public final static int VARARG = 1;

	public final static int BLOCK = 2;

	private ASTNode value;

	private int kind;

	public ASTNode getValue() {
		return value;
	}

	public void setValue(ASTNode value) {
		this.value = value;
	}

	public RubyCallArgument(ASTNode value) {
		super(value.sourceStart(), value.sourceEnd());
		this.value = value;
	}

	public RubyCallArgument(ASTNode value, int kind) {
		super(value.sourceStart(), value.sourceEnd());
		this.value = value;
		this.kind = kind;
	}

	public int getArgumentKind() {
		return kind;
	}

	@Override
	public void printNode(CorePrinter output) {
		if (kind == VARARG) {
			output.append("[VARARG]"); //$NON-NLS-1$
		} else if (kind == BLOCK) {
			output.append("[BLOCK]"); //$NON-NLS-1$
		}
		if (value != null) {
			value.printNode(output);
		}
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (getValue() != null)
				getValue().traverse(visitor);
			visitor.endvisit(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RubyCallArgument))
			return false;
		RubyCallArgument arg = (RubyCallArgument) obj;
		// FIXME WTF?
		return (arg.kind == kind && arg.value == value);
	}

}
