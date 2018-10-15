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

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;

public class RubyMixinElementInfo {

	public static final int K_CLASS = 0;

	public static final int K_MODULE = 1;

	public static final int K_METHOD = 2;

	public static final int K_VARIABLE = 3;

	public static final int K_INCLUDE = 4;

	public static final int K_VIRTUAL = 5;

	public static final int K_SUPER = 6;

	public static final int K_ALIAS = 7;

	public static final int K_EXTEND = 8;

	private int kind = 0;
	private Object object = null;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kind;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		final RubyMixinElementInfo other = (RubyMixinElementInfo) obj;
		if (kind != other.kind)
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

	public RubyMixinElementInfo(int kind, Object object) {
		super();
		this.kind = kind;
		this.object = object;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static RubyMixinElementInfo createClass(IType type) {
		return new RubyMixinElementInfo(K_CLASS, type);
	}

	public static RubyMixinElementInfo createModule(IType type) {
		return new RubyMixinElementInfo(K_MODULE, type);
	}

	public static RubyMixinElementInfo createMethod(IMethod m) {
		return new RubyMixinElementInfo(K_METHOD, m);
	}

	public static RubyMixinElementInfo createInclude(String key) {
		return new RubyMixinElementInfo(K_INCLUDE, key);
	}

	public static RubyMixinElementInfo createExtend(String key) {
		return new RubyMixinElementInfo(K_EXTEND, key);
	}

	public static RubyMixinElementInfo createSuperClass(String key) {
		return new RubyMixinElementInfo(K_SUPER, key);
	}

	public static RubyMixinElementInfo createVariable(IField type) {
		return new RubyMixinElementInfo(K_VARIABLE, type);
	}

	public static RubyMixinElementInfo createVirtualClass() {
		return new RubyMixinElementInfo(K_VIRTUAL, null);
	}

}
