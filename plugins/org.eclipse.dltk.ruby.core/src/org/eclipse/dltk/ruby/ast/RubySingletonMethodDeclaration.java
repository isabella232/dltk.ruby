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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.statements.Block;

public class RubySingletonMethodDeclaration extends MethodDeclaration {
	private final ASTNode receiver;

	public RubySingletonMethodDeclaration(String name, int nameStart,
			int nameEnd, int declStart, int declEnd, ASTNode receiver) {
		super(name, nameStart, nameEnd, declStart, declEnd);
		this.receiver = receiver;
	}

	public ASTNode getReceiver() {
		return receiver;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		Block body = this.getBody();
		if (visitor.visit(this)) {
			
			// Receiver
			if (receiver != null)
				receiver.traverse(visitor);

			// Arguments
			Iterator<Argument> it = arguments.iterator();
			while (it.hasNext()) {
				Argument arg = it.next();
				arg.traverse(visitor);
			}

			// Body
			if (body != null) {
				body.traverse(visitor);
			}

			visitor.endvisit(this);
		}
	}
}
