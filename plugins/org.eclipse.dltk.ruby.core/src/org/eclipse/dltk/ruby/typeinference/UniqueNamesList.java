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

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.dltk.core.IModelElement;

class UniqueNamesList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 4866432224140371654L;
	
	HashSet<String> names = new HashSet<String> ();
	
	@Override
	public boolean add(T elem) {
		if (elem instanceof IModelElement) {
			IModelElement modelElement = (IModelElement) elem;
			if (names.contains(modelElement.getElementName()))
					return false;
			names.add(modelElement.getElementName());
		}
		return super.add(elem);
	}

	@Override
	public void clear() {			
		super.clear();
		names.clear();
	}

	@Override
	public boolean contains(Object elem) {
		if (elem instanceof IModelElement) {
			IModelElement modelElement = (IModelElement) elem;
			return names.contains(modelElement.getElementName());
		}
		return super.contains(elem);
	}

}