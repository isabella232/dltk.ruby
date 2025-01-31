/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ruby.core.model.ICalculatedType;
import org.eclipse.dltk.ruby.core.model.IElement;
import org.eclipse.dltk.ruby.core.model.IElementCriteria;
import org.eclipse.dltk.ruby.core.model.IElementKind;
import org.eclipse.dltk.ruby.core.model.IMethod;

public class RubyTypeCalculator {
	
	private final Model model;

	public RubyTypeCalculator(Model model) {
		this.model = model;
	}

	public ICalculatedType calculateType(ASTNode node) {
		if (node instanceof NumericLiteral) {
			NumericLiteral expression = (NumericLiteral) node;
			// TODO: handle user contributions into Fixnum
			return createFixnum();
		} 
		return null;
	}

	private ICalculatedType createFixnum() {
		final ICalculatedType type = new ICalculatedType() {

			@Override
			public IElement[] findChildren(IElementCriteria criteria, String name, IProgressMonitor pm) {
				if (criteria == IElementCriteria.ByKind.METHOD) {
					final IElement typeParent = this;
					IElement ceil = new IMethod() {

						@Override
						public IElement[] findChildren(IElementCriteria criteria, String name, IProgressMonitor pm) {
							return IElement.EMPTY_ARRAY;
						}

						@Override
						public IElement getAncestor(IElementCriteria criteria) {
							if (criteria == IElementCriteria.ByKind.MODEL)
								return model;
							else if (criteria == IElementCriteria.ByKind.CLASS
									|| criteria == IElementCriteria.CLASS_OR_MIXIN)
								return typeParent;
							return null;
						}

						@Override
						public IElementKind getElementKind() {
							return IElementKind.METHOD;
						}

						@Override
						public String getName() {
							return "ceil"; //$NON-NLS-1$
						}
						
					};
					return new IElement[] {ceil};
				}
				return IElement.EMPTY_ARRAY;
			}

			@Override
			public IElement getAncestor(IElementCriteria criteria) {
				if (criteria == IElementCriteria.ByKind.MODEL)
					return model;
				return null;
			}

			@Override
			public IElementKind getElementKind() {
				return IElementKind.CLASS;
			}
			
		};
		return type;
	}
	
}
