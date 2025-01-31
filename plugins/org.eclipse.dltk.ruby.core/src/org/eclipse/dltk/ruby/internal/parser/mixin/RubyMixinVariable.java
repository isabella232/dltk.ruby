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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.mixin.IMixinElement;

public class RubyMixinVariable implements IRubyMixinElement {

	private final String key;
	private final RubyMixinModel model;

	public RubyMixinVariable(RubyMixinModel model, String key) {
		super();
		this.model = model;
		this.key = key;
	}

	@Override
	public String getKey() {
		return key;
	}
	
	public IField[] getSourceFields () {
		List<IField> result = new ArrayList<IField> ();
		IMixinElement mixinElement = model.getRawModel().get(key);
		Object[] allObjects = mixinElement.getAllObjects();
		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info.getKind() == RubyMixinElementInfo.K_VARIABLE) {
				result.add ((IField) info.getObject());							
			}
		}
		return result.toArray(new IField[result.size()]);
	}
	

}
