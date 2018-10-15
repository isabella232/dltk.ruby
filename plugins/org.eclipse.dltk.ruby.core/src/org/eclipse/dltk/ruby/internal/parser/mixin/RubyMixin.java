/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parser.mixin;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.mixin.IMixinParser;
import org.eclipse.dltk.core.mixin.IMixinRequestor;

public class RubyMixin implements IMixinParser {

	// suffix for instance classes
	public final static String INSTANCE_SUFFIX = "%"; //$NON-NLS-1$

	// suffix for virtual classes
	public final static String VIRTUAL_SUFFIX = "%v"; //$NON-NLS-1$

	private IMixinRequestor requestor;

	@Override
	public void parserSourceModule(boolean signature, ISourceModule module) {
		try {
			ModuleDeclaration moduleDeclaration = SourceParserUtil
					.getModuleDeclaration(module);
			RubyMixinBuildVisitor visitor = new RubyMixinBuildVisitor(
					moduleDeclaration, module, signature, requestor);
			moduleDeclaration.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRequirestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}
}
