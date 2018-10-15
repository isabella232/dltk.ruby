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

import org.eclipse.dltk.evaluation.types.IClassType;
import org.eclipse.dltk.ti.types.ClassType;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class RubyClassType extends ClassType implements IClassType {

	private String modelKey;

	public RubyClassType(String modelKey) {
		this.modelKey = modelKey;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		return false; //TODO
	}

	@Override
	public String getModelKey() {
		return modelKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((modelKey == null) ? 0 : modelKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RubyClassType other = (RubyClassType) obj;
		if (modelKey == null) {
			if (other.modelKey != null)
				return false;
		} else if (!modelKey.equals(other.modelKey))
			return false;
		return true;
	}
	
	

}
