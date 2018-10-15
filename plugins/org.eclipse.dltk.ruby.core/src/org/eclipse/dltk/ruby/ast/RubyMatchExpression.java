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

public class RubyMatchExpression extends ASTNode {

	private final ASTNode regexp;

	public RubyMatchExpression(int start, int end, ASTNode regexp) {
		super(start, end);
		this.regexp = regexp;
	}

	public ASTNode getRegexp() {
		return regexp;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {

		if( visitor.visit( this ) ) {
			if( this.regexp != null ) {
				this.regexp.traverse( visitor );
			}			
		}
		visitor.endvisit( this );

	}

}
