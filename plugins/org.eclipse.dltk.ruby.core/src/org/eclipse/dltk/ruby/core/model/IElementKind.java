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

import org.eclipse.dltk.ast.references.VariableKind;

public interface IElementKind {
	
	public static final int FIRST_NON_VARIABLE_ID = VariableKind.LAST_VARIABLE_ID + 1;

	int getId();
	
	public static class Model implements IElementKind {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 0;

		@Override
		public int getId() {
			return ID;
		}
		
	}
	
	public static class Unit implements IElementKind {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 1;

		@Override
		public int getId() {
			return ID;
		}
		
	}

	public static class Method implements IElementKind {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 2;

		@Override
		public int getId() {
			return ID;
		}
		
	}
	
	public static abstract class ClassLike implements IElementKind {
		
	}
	
	public static abstract class Fragment implements IElementKind {
		
	}
	
	public static class Class extends ClassLike {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 3;

		@Override
		public int getId() {
			return ID;
		}
		
		
	}
	
	public static class Mixin extends ClassLike {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 4;

		@Override
		public int getId() {
			return ID;
		}

	}
	
	public static class ClassFragment extends Fragment {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 5;

		@Override
		public int getId() {
			return ID;
		}

		
	}
	
	public static class MixinFragment extends Fragment {
		
		public static final int ID = FIRST_NON_VARIABLE_ID + 6;

		@Override
		public int getId() {
			return ID;
		}

	}
	
	public static class Variable implements IElementKind {
		
		private final VariableKind kind;

		public Variable(VariableKind kind) {
			this.kind = kind;
		}

		public VariableKind getKind() {
			return kind;
		}

		@Override
		public int getId() {
			return kind.getId();
		}
		
		protected static final Map<VariableKind, Variable> variableKindsToElementKinds = new HashMap<VariableKind, Variable>();
		
		static {
			variableKindsToElementKinds.put(VariableKind.LOCAL, new Variable(VariableKind.LOCAL));
			variableKindsToElementKinds.put(VariableKind.INSTANCE, new Variable(VariableKind.INSTANCE));
			variableKindsToElementKinds.put(VariableKind.CLASS, new Variable(VariableKind.CLASS));
			variableKindsToElementKinds.put(VariableKind.GLOBAL, new Variable(VariableKind.GLOBAL));
			variableKindsToElementKinds.put(VariableKind.MIXIN, new Variable(VariableKind.MIXIN));
			variableKindsToElementKinds.put(VariableKind.ARGUMENT, new Variable(VariableKind.ARGUMENT));
		}
		
		public static Variable byVariableKind(VariableKind kind) {
			return variableKindsToElementKinds.get(kind);
		}
		
	}
	
	public static final int LAST_CORE_ID = FIRST_NON_VARIABLE_ID + 50;
	
	public static final int LAST_ID = LAST_CORE_ID + 50;

	public static final IElementKind MODEL = new Method();
	
	public static final IElementKind METHOD = new Method();
	
	public static final IElementKind CLASS = new Class();
	
	public static final IElementKind MIXIN = new Mixin();
	
	public static final IElementKind CLASS_FRAGMENT = new ClassFragment();
	
	public static final IElementKind MIXIN_FRAGMENT = new MixinFragment();
	
	public static final IElementKind LOCAL_VARIABLE = Variable.byVariableKind(VariableKind.LOCAL);
	
	public static final IElementKind INSTANCE_VARIABLE = Variable.byVariableKind(VariableKind.INSTANCE);
	
	public static final IElementKind CLASS_VARIABLE = Variable.byVariableKind(VariableKind.CLASS);
	
	public static final IElementKind GLOBAL_VARIABLE = Variable.byVariableKind(VariableKind.GLOBAL);
	
	public static final IElementKind MIXIN_VARIABLE = Variable.byVariableKind(VariableKind.MIXIN);
	
	public static final IElementKind ARGUMENT_VARIABLE = Variable.byVariableKind(VariableKind.ARGUMENT);
	
}
