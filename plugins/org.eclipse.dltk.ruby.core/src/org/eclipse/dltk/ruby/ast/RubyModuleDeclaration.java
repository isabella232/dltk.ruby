/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ast;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Block;

public class RubyModuleDeclaration extends TypeDeclaration {

	private ASTNode name;

	public RubyModuleDeclaration(ASTNode name, Block body, int start, int end) {
		super(RubyASTUtil.resolveClassName(name), name.sourceStart(), name
				.sourceEnd(), start, end);
		this.name = name;
		this.fBody = body;
		setStart(start);
		setEnd(end);
	}

	public ASTNode getClassName() {
		return name;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (name != null) {
				if (visitor instanceof IRubyASTVisitor) {
					((IRubyASTVisitor) visitor).visitTypeName(name);
				} else {
					name.traverse(visitor);
				}
			}
			if (this.getBody() != null) {
				getBody().traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	@Override
	public String resolveSuperClassReference(ASTNode node) {
		return RubyASTUtil.resolveClassName(node);
	}

}
