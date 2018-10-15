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
import org.eclipse.dltk.utils.CorePrinter;


/**
 * Assignment expression used to hold a = b expressions.
 */
public class RubyAssignment extends RubyBinaryExpression
{
	
	public static final RubyAssignment[] EMPTY_ARRAY = new RubyAssignment[0];

	/**
	 * Construct from left, right and type expression. Used to construct NotStrictAssignment class.
	 * 
	 * @param left
	 * @param type
	 * @param right
	 */
	protected RubyAssignment( ASTNode left, int type, ASTNode right ) {

		super( left, type, right );
	}

	/**
	 * Construct default strict assignment.
	 * 
	 * @param left
	 * @param right
	 */
	public RubyAssignment( ASTNode left, ASTNode right ) {

		super( left, E_ASSIGN, right );
	}

	/**
	 * Convert to string in pattern: "left = right"
	 */
	@Override
	public String toString( ) {
		return String.valueOf(getLeft()) + '=' + String.valueOf(getRight());
	}

	/**
	 * Testing purposes only. Used to print expression.
	 */
	@Override
	public void printNode( CorePrinter output ) {

		if( getLeft() != null ) {
			getLeft().printNode( output );
		}
		output.formatPrintLn( " = " ); //$NON-NLS-1$
		if( getRight() != null ) {
			getRight().printNode( output );
		}
	}
}
