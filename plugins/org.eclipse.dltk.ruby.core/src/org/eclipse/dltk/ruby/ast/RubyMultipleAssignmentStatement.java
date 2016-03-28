/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.utils.CorePrinter;

public class RubyMultipleAssignmentStatement extends ASTNode {

	private List<ASTNode> lhs = new ArrayList<ASTNode>();
	private List<ASTNode> rhs = new ArrayList<ASTNode>();

	private ASTNode leftAsterix;
	private ASTNode rightAsterix;

	public RubyMultipleAssignmentStatement(int start, int end) {
		super(start, end);
	}

	public void addLhs(ASTNode s) {
		if (s == null)
			return;
		if (this.sourceStart() == -1 ||  s.sourceStart() < this.sourceStart())
			setStart(s.sourceStart());
		lhs.add(s);
	}

	public void addRhs(ASTNode s) {
		if (s == null)
			return;
		if (this.sourceEnd() == -1 ||  s.sourceEnd() > this.sourceEnd())
			setEnd(s.sourceEnd());
		rhs.add(s);
	}

	public List<ASTNode> getLhs() {
		return lhs;
	}

	public List<ASTNode> getRhs() {
		return rhs;
	}

	public ASTNode getLeftAsterix() {
		return leftAsterix;
	}

	public void setLeftAsterix(ASTNode leftAsterix, int offset) {
		if (this.sourceStart() == -1 ||  offset < this.sourceStart())
			setStart(offset);
		this.leftAsterix = leftAsterix;
	}

	public ASTNode getRightAsterix() {
		return rightAsterix;
	}

	public void setRightAsterix(ASTNode rightAsterix) {
		if (this.sourceEnd() == -1 ||  rightAsterix.sourceEnd() > this.sourceEnd())
			setEnd(rightAsterix.sourceEnd());
		this.rightAsterix = rightAsterix;
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
			if (this.leftAsterix != null)
				leftAsterix.traverse(visitor);
			if (this.rightAsterix != null)
				rightAsterix.traverse(visitor);
			if (this.lhs != null)
				for (Iterator<ASTNode> iterator = this.lhs.iterator(); iterator
						.hasNext();) { ASTNode v = iterator.next();
					if (v != null)
						v.traverse(visitor);
				}
			if (this.rhs != null)
				for (Iterator<ASTNode> iterator = this.rhs.iterator(); iterator
						.hasNext();) { ASTNode v = iterator.next();
					if (v != null)
						v.traverse(visitor);
				}
			visitor.endvisit(this);
		}
	}

}
