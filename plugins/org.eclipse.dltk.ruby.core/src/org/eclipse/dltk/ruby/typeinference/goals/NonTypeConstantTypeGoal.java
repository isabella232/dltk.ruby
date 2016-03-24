/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference.goals;

import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;

/**
 * Evaluates type of a given constant element, that are not a Class,
 * for ex.:
 * <code>A = 4</code>
 */
public class NonTypeConstantTypeGoal extends AbstractTypeGoal {

	private final IMixinElement element;

	public NonTypeConstantTypeGoal(IContext context, IMixinElement element) {
		super(context);
		this.element = element;
	}

	public IMixinElement getElement() {
		return element;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonTypeConstantTypeGoal other = (NonTypeConstantTypeGoal) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}
	
	

}
