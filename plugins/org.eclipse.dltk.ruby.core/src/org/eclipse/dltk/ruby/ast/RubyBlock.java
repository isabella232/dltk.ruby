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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;

public class RubyBlock extends ASTNode {

	private Map<ASTNode, ASTNode> vars = new HashMap<ASTNode, ASTNode>();
	private ASTNode asterixParameter = null;
	private ASTNode body;

	public RubyBlock(int start, int end, ASTNode body) {
		super(start, end);
		this.body = body;
	}

	public ASTNode getAsterixParameter() {
		return asterixParameter;
	}

	public void setAsterixParameter(ASTNode asterixParameter) {
		this.asterixParameter = asterixParameter;
	}

	public RubyBlock(int start, int end) {
		super(start, end);
	}

	public Set<ASTNode> getVars() {
		return vars.keySet();
	}

	public void addVar(ASTNode var) {
		addVar(var, null);
	}

	public void addVar(ASTNode var, ASTNode defaultValue) {
		vars.put(var, defaultValue);
	}

	public ASTNode getBody() {
		return body;
	}

	public void setBody(ASTNode body) {
		this.body = body;
	}

	public int getKind() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			for (Iterator<ASTNode> iterator = vars.keySet().iterator(); iterator
					.hasNext();) {
				ASTNode var = iterator.next();
				if (var != null)
					var.traverse(visitor);
			}
			for (Iterator<ASTNode> iterator = vars.values().iterator(); iterator
					.hasNext();) {
				ASTNode var = iterator.next();
				if (var != null)
					var.traverse(visitor);
			}
			if (body != null)
				body.traverse(visitor);
			if (asterixParameter != null)
				asterixParameter.traverse(visitor);
			visitor.endvisit(this);
		}
	}

}
