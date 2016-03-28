/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ast;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;

public class RubyCallArgumentsList extends CallArgumentsList {
	
	public void addArgument(ASTNode value, int kind) {
		if (value != null) {
			this.addNode(new RubyCallArgument(value, kind));
		}
	}
	
	public void autosetOffsets() {
		final List<ASTNode> expressions = this.getChilds();
		final int size = expressions.size();
		if (size > 0) {
			final ASTNode first = expressions.get(0);
			final ASTNode last = expressions.get(size - 1);
			this.setStart(first.sourceStart());
			this.setEnd(last.sourceEnd());
		}
	}

}
