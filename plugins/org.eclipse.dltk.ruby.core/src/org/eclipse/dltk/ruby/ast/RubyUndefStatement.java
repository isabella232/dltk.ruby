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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.utils.CorePrinter;

public class RubyUndefStatement extends ASTNode {

	private final List<String> parameters = new ArrayList<String> ();

	public List<String> getParameters() {
		return parameters;
	}

	public RubyUndefStatement(int start, int end) {
		super(start, end);
	}
	
	public void addParameter (String p) {
		this.parameters.add(p);
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printNode(CorePrinter output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {			
			visitor.endvisit(this);
		}
	}

}
