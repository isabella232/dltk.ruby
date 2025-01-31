/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.ruby.ast.RubyBlock;

public class OffsetTargetedASTVisitor extends ASTVisitor {

	private final int requestedOffset;

	public OffsetTargetedASTVisitor(int requestedOffset) {
		this.requestedOffset = requestedOffset;
	}

	protected boolean interesting(ASTNode s) {
		// XXX AST offsets bug workaround
		if (s instanceof Block)
			return true;
		if (s.sourceStart() >= 0
				&& s.sourceEnd() > s.sourceStart()
				&& (requestedOffset < s.sourceStart() || requestedOffset >= s
						.sourceEnd()))
			return false;
		return true;
	}

	@Override
	public final boolean visit(MethodDeclaration s) {
		if (!interesting(s))
			return false;
		return visitInteresting(s);
	}

	protected boolean visitInteresting(MethodDeclaration s) {
		return visitGeneralInteresting(s);
	}

	@Override
	public final boolean visit(ModuleDeclaration s) {
		if (!interesting(s))
			return false;
		return visitInteresting(s);
	}

	protected boolean visitInteresting(ModuleDeclaration s) {
		return visitGeneralInteresting(s);
	}

	@Override
	public final boolean visit(TypeDeclaration s) throws Exception {
		if (!interesting(s))
			return false;
		return visitInteresting(s);
	}

	protected boolean visitInteresting(TypeDeclaration s) throws Exception {
		return visitGeneralInteresting(s);
	}

	@Override
	public final boolean visit(Expression s) throws Exception {
		if (!interesting(s))
			return false;
		return visitInteresting(s);
	}

	protected boolean visitInteresting(Expression s) {
		return visitGeneralInteresting(s);
	}

	@Override
	public final boolean visit(Statement s) throws Exception {
		if (!interesting(s))
			return false;
		return visitInteresting(s);
	}

	protected boolean visitInteresting(Statement s) {
		return visitGeneralInteresting(s);
	}

	protected boolean visitInteresting(RubyBlock b) {
		return true;
	}

	@Override
	public final boolean visitGeneral(ASTNode s) throws Exception {
		if (!interesting(s))
			return false;
		if (s instanceof RubyBlock) {
			return visitInteresting((RubyBlock) s);
		}
		return visitGeneralInteresting(s);
	}

	protected boolean visitGeneralInteresting(ASTNode s) {
		return true;
	}

}
