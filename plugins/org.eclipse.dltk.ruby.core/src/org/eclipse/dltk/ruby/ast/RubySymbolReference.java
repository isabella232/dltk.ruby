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

import org.eclipse.dltk.ast.references.Reference;
import org.eclipse.dltk.utils.CorePrinter;

public class RubySymbolReference extends Reference {
	
	private String name;

	public RubySymbolReference (int start, int end, String name) {
		super(start, end);
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn("RubySymbolReference" + this.getSourceRange().toString()); //$NON-NLS-1$
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RubySymbolReference) {
			RubySymbolReference sr = (RubySymbolReference)obj;			
			return name.equals(sr.name) && super.equals(obj);
		}
				
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ name.hashCode();
	}

	@Override
	public String getStringRepresentation() {
		return toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
