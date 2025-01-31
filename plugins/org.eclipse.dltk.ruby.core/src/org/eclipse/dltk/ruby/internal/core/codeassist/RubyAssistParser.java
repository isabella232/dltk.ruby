/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core.codeassist;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.SourceParserUtil;

public abstract class RubyAssistParser implements IAssistParser {

	protected ModuleDeclaration module;

	protected ASTNode assistNodeParent = null;

	@Override
	public ASTNode getAssistNodeParent() {
		return assistNodeParent;
	}

	@Override
	public void setSource(ModuleDeclaration unit) {
		this.module = unit;
	}

	@Override
	public ModuleDeclaration parse(IModuleSource sourceUnit) {
		ModuleDeclaration module = null;
		module = SourceParserUtil
				.getModuleDeclaration((org.eclipse.dltk.core.ISourceModule) sourceUnit
						.getModelElement());

		return module;
	}
}
