/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *     xored software, Inc. - RubyDocumentation display improvements (Alex Panchenko <alex@xored.com>)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parser.mixin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;

public class RubyMixinMethod implements IRubyMixinElement {

	private final String key;
	protected final RubyMixinModel model;
	private IMethod[] sourceMethods;

	public RubyMixinMethod(RubyMixinModel model, String key) {
		super();
		this.model = model;
		this.key = key;
	}

	public String getName() {
		return key.substring(key.lastIndexOf(MixinModel.SEPARATOR) + 1);
	}

	public RubyMixinMethod(RubyMixinModel model, String key,
			IMethod[] sourceMethods) {
		super();
		this.model = model;
		this.key = key;
		this.sourceMethods = sourceMethods;
	}

	@Override
	public String getKey() {
		return key;
	}

	public RubyMixinClass getSelfType() {
		IMixinElement mixinElement = model.getRawModel().get(key);
		IMixinElement parent = null;
		if (mixinElement != null)
			parent = mixinElement.getParent();
		if (parent == null)
			return new RubyObjectMixinClass(model, true);
		IRubyMixinElement rubyParent = model.createRubyElement(parent);
		if (rubyParent instanceof RubyMixinClass) {
			return (RubyMixinClass) rubyParent;
		}
		return null;
	}

	/**
	 * Allows to set precalculated source methods and not use mixin model to
	 * find them.
	 */
	public void setSourceMethods(IMethod[] sourceMethods) {
		this.sourceMethods = sourceMethods;
	}

	/**
	 * Returns model elements for this method. If they were previously saved
	 * using setSourceMethods() or constructor, then exactly they are returned.
	 * Else mixin model are used.
	 */
	public IMethod[] getSourceMethods() {
		if (this.sourceMethods != null)
			return sourceMethods;
		return RubyMixinMethod.getSourceMethods(model, key);
	}

	protected static IMethod[] getSourceMethods(RubyMixinModel model, String key) {
		final IMixinElement mixinElement = model.getRawModel().get(key);
		if (mixinElement != null) {
			final Object[] allObjects = mixinElement.getAllObjects();
			final List<IMethod> result = new ArrayList<IMethod>();
			for (int i = 0; i < allObjects.length; i++) {
				RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
				if (info.getKind() == RubyMixinElementInfo.K_METHOD) {
					result.add((IMethod) info.getObject());
				}
			}
			return result.toArray(new IMethod[result.size()]);
		}
		return new IMethod[0];
	}

	public RubyMixinVariable[] getVariables() {
		List<RubyMixinVariable> result = new ArrayList<RubyMixinVariable>();
		IMixinElement mixinElement = model.getRawModel().get(key);
		IMixinElement[] children = mixinElement.getChildren();
		for (int i = 0; i < children.length; i++) {
			IRubyMixinElement element = model.createRubyElement(children[i]);
			if (element instanceof RubyMixinVariable)
				result.add((RubyMixinVariable) element);
		}
		return result
				.toArray(new RubyMixinVariable[result.size()]);
	}
	
	@Override
	public String toString() {
		return "RubyMixinMethod[" + key + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
