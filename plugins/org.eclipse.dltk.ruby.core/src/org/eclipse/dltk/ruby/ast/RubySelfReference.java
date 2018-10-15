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

public class RubySelfReference extends Reference {

	public RubySelfReference(int start, int end) {
		super(start, end);
	}

	@Override
	public String getStringRepresentation() {
		return "self"; //$NON-NLS-1$
	}

	@Override
	public void printNode(CorePrinter output) {
		output.formatPrintLn("SelfReference" + this.getSourceRange().toString()); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return "self"; //$NON-NLS-1$
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RubySelfReference)) {
			return false;
		}
		RubySelfReference d = (RubySelfReference) obj;
		return sourceStart() == d.sourceStart() && sourceEnd() == d.sourceEnd();
	}

	@Override
	public int hashCode() {
		return sourceStart() ^ sourceEnd();
	}
}
