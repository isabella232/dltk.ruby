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

/**
 * This class is descendant of RubyMixinClass specified for ruby "Object" class.
 * The idea was to additionally search for the top-level methods (defined at the
 * source module scope), but that is not needed anymore since now they are
 * indexed as part of the "Object" class.
 * 
 * @see org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinBuildVisitor.SourceModuleScope
 */
public class RubyObjectMixinClass extends RubyMixinClass {

	public RubyObjectMixinClass(RubyMixinModel model, boolean meta) {
		super(model, meta ? RubyMixinUtils.OBJECT
				: RubyMixinUtils.OBJECT_INSTANCE, false);
	}

	@Override
	public RubyMixinVariable[] getFields() {
		return new RubyMixinVariable[0];
	}

}
