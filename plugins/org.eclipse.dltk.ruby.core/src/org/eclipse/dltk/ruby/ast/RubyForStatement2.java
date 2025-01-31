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

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

/**
 * For Statement.
 *  for <target> in <list> do 
 *  	<action>
 *  end
 */
public class RubyForStatement2 extends ASTNode {
	

	private ASTNode fTarget;
	private ASTListNode fList;
	private ASTNode fAction;
	
	
	public RubyForStatement2(int start, int end, ASTNode target,
			ASTListNode list, ASTNode action) {
		super(start, end);
		fTarget = target;
		fList = list;
		fAction = action;
	}
	
	

	public RubyForStatement2(int start, int end) {
		super(start, end);
	}



	public void setTarget(ASTNode target) {
		fTarget = target;
	}



	public void setList(ASTListNode list) {
		fList = list;
	}



	public void setAction(ASTNode action) {
		fAction = action;
	}



	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {
			if (fTarget != null) {
				fTarget.traverse(pVisitor);
			}
			if (fList != null) {
				fList.traverse(pVisitor);
			}			
			if (fAction != null) {
				fAction.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public ASTNode getAction() {
		return fAction;
	}

	
	public ASTNode getTarget() {
		return fTarget;
	}

	public ASTListNode getList() {
		return fList;
	}
	
}
