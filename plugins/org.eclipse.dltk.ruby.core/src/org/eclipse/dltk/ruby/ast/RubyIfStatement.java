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
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;


/**
 * If statement.
 */
public class RubyIfStatement extends ASTNode {
	/**
	 * Condition expression.
	 */
	private ASTNode fCondition;

	/**
	 * Then statement of if.
	 */
	private ASTNode fThenStatement;

	/**
	 * Else statement of if. Can be null, or possible EmptyStatement..
	 */
	private ASTNode fElseStatement;

	public RubyIfStatement(ASTNode condition, ASTNode thenStatement, ASTNode elseStatement) {
		this.fCondition = condition;
		this.fThenStatement = thenStatement;
		this.fElseStatement = elseStatement;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {
			if (fCondition != null) {
				fCondition.traverse(pVisitor);
			}
			if (fThenStatement != null) {
				fThenStatement.traverse(pVisitor);
			}
			if (fElseStatement != null) {
				fElseStatement.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	

	/**
	 * Acccept Else statement.
	 * 
	 * @param elseStatement
	 */
	public void acceptElse(ASTNode elseStatement) {
		this.fElseStatement = elseStatement;
		if (this.fElseStatement != null) {
			this.setEnd(this.fElseStatement.sourceEnd());
		}
	}

	/**
	 * Return else statement.
	 * 
	 * @return - else statement. Be aware can be null.
	 */
	public ASTNode getElse() {
		return fElseStatement;
	}

	public ASTNode getThen() {
		return fThenStatement;
	}

	public ASTNode getCondition() {
		return this.fCondition;
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn("if: "); //$NON-NLS-1$
		if (this.fCondition != null) {
			this.fCondition.printNode(output);
		}
		if (this.fThenStatement != null) {
			if (!(this.fThenStatement instanceof Block)) {
				output.indent();
			}
			this.fThenStatement.printNode(output);
			if (!(this.fThenStatement instanceof Block)) {
				output.dedent();
			}
		}
		if (this.fElseStatement != null) {
			output.formatPrintLn("else:"); //$NON-NLS-1$
			if (!(this.fElseStatement instanceof Block)) {
				output.indent();
			}
			this.fElseStatement.printNode(output);
			if (!(this.fElseStatement instanceof Block)) {
				output.dedent();
			}
		}

	}
}
