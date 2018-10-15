/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model;

import java.util.HashMap;
import java.util.Map;

public interface IElementCriteria {

	public static class ByKind implements IElementCriteria {
	
		private final IElementKind kind;

		public ByKind(IElementKind kind) {
			this.kind = kind;
		}

		public IElementKind getKind() {
			return kind;
		}
		
		protected static final Map<IElementKind, ByKind> elementKindsToCriterias = new HashMap<IElementKind, ByKind>();
		
		private static void add(IElementKind kind) {
			elementKindsToCriterias.put(kind, new ByKind(kind));
		}
		
		static {
			add(IElementKind.MODEL);
			add(IElementKind.CLASS);
			add(IElementKind.MIXIN);
			add(IElementKind.CLASS_FRAGMENT);
			add(IElementKind.MIXIN_FRAGMENT);
			add(IElementKind.METHOD);
			add(IElementKind.GLOBAL_VARIABLE);
			add(IElementKind.INSTANCE_VARIABLE);
			add(IElementKind.CLASS_VARIABLE);
			add(IElementKind.MIXIN_VARIABLE);
			add(IElementKind.ARGUMENT_VARIABLE);
		}
		
		public static ByKind byElementKind(IElementKind kind) {
			return elementKindsToCriterias.get(kind);
		}
		
		public static final IElementCriteria MODEL = byElementKind(IElementKind.MODEL);
		
		public static final IElementCriteria METHOD = byElementKind(IElementKind.METHOD);
		
		public static final IElementCriteria CLASS = byElementKind(IElementKind.CLASS);
		
		public static final IElementCriteria MIXIN = byElementKind(IElementKind.MIXIN);
		
		public static final IElementCriteria CLASS_FRAGMENT = byElementKind(IElementKind.CLASS_FRAGMENT);
		
		public static final IElementCriteria MIXIN_FRAGMENT = byElementKind(IElementKind.MIXIN_FRAGMENT);
		
		public static final IElementCriteria LOCAL_VARIABLE = byElementKind(IElementKind.LOCAL_VARIABLE);
		
		public static final IElementCriteria INSTANCE_VARIABLE = byElementKind(IElementKind.INSTANCE_VARIABLE);
		
		public static final IElementCriteria CLASS_VARIABLE = byElementKind(IElementKind.CLASS_VARIABLE);
		
		public static final IElementCriteria GLOBAL_VARIABLE = byElementKind(IElementKind.GLOBAL_VARIABLE);
		
		public static final IElementCriteria MIXIN_VARIABLE = byElementKind(IElementKind.MIXIN_VARIABLE);
		
		public static final IElementCriteria ARGUMENT_VARIABLE = byElementKind(IElementKind.ARGUMENT_VARIABLE);
		
	}
	
	public static final IElementCriteria CLASS_OR_MIXIN = new IElementCriteria() {
	};
	
	public static final IElementCriteria CLASS_OR_MIXIN_FRAGMENT = new IElementCriteria() {
	};
	
}
