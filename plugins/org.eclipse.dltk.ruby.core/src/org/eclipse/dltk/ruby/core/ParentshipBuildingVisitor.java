/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class ParentshipBuildingVisitor extends ASTVisitor {

	private final Map<ASTNode, ASTNode> parents = new HashMap<ASTNode, ASTNode>();
	
	private final ArrayList<ASTNode> stack = new ArrayList<ASTNode>();
	
	private void push(ASTNode node) {
		stack.add(node);
	}
	
	private ASTNode peek() {
		return stack.get(stack.size() - 1);
	}
	
	private void pop() {
		stack.remove(stack.size() - 1);
	}

	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		if (!stack.isEmpty())
			parents.put(node, peek());
		push(node);
		return true;
	}
	
	@Override
	public void endvisitGeneral(ASTNode node) throws Exception {
		Assert.isTrue(node == peek());
		pop();
	}

	public Map<ASTNode, ASTNode> getParents() {
		return parents;
	}
	
}
