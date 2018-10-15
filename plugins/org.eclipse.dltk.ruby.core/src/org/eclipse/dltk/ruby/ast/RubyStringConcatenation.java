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

public class RubyStringConcatenation extends ASTListNode {

	public RubyStringConcatenation(int start, int end) {
		super(start, end);
	}

	@Override
	public void addNode(ASTNode s) {
		super.addNode(s);
		if (this.sourceStart() == -1)
			this.setStart(s.sourceStart());
		if (this.sourceEnd() == -1)
			this.setEnd(s.sourceEnd());
	}
	
	

}
