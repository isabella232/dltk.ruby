/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core.codeassist;


/**
 * A selection requestor accepts results from the selection engine.
 * XXX: by now it is Ruby specific, generalize later
 */
public interface IRubySelectionRequestor {
	
	// declaration type flag
	public static final int DECL_INSTANCE = 1;
	public static final int DECL_CLASS = 2;
	
	void acceptClass(		
		char[] declaringFileName,
		char[] qualifiedTypeName);
	
	void acceptModule(		
		char[] declaringFileName,
		char[] qualifiedTypeName);
	
	// global, instance or class variable
	void acceptField(
		char[] declaringFileName,
		char[] declaringTypeName,
		char[] name, //name without $, @, @@ 
		int declFlags,
		boolean isGlobal);
	
	void acceptLocalVariable(			
			char[] name,  
			int selectionStart, 
			int selectionEnd);
	
	void acceptMethod(
		char[] declaringFileName,
		char[] declaringTypeName,
		char[] selector,		
		int declFlags);
	
	
}
