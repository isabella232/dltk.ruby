/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.references.VariableKind;

public interface IVariable extends IElement {

	VariableKind getVariableKind();

	String getName();
	
	ICalculatedType calculateType(IProgressMonitor progress);

}
