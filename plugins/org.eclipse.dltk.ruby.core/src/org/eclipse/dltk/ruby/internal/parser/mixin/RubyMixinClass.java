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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.DLTKTypeInferenceEngine;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class RubyMixinClass implements IRubyMixinElement {

	private final String key;
	protected final RubyMixinModel model;
	private final boolean module;

	public RubyMixinClass(RubyMixinModel model, String key, boolean module) {
		super();
		this.model = model;
		this.key = key;
		this.module = module;
	}

	@Override
	public String getKey() {
		return key;
	}

	public boolean isModule() {
		return module;
	}

	public RubyMixinClass getInstanceClass() {
		if (!isMeta())
			return this;
		String newkey = key + RubyMixin.INSTANCE_SUFFIX;
		IRubyMixinElement r = model.createRubyElement(newkey);
		if (r instanceof RubyMixinClass)
			return (RubyMixinClass) r;
		return null;
	}

	public RubyMixinClass getMetaclass() {
		if (isMeta())
			return this;
		String metakey = key.substring(0, key
				.indexOf(RubyMixin.INSTANCE_SUFFIX));
		IRubyMixinElement r = model.createRubyElement(metakey);
		if (r instanceof RubyMixinClass)
			return (RubyMixinClass) r;
		return null;
	}

	public boolean isMeta() {
		return (!key.endsWith(RubyMixin.INSTANCE_SUFFIX))
				&& (!key.endsWith(RubyMixin.VIRTUAL_SUFFIX));
	}

	public String getName() {
		String name;
		if (key.indexOf(MixinModel.SEPARATOR) != -1)
			name = key.substring(key.lastIndexOf(MixinModel.SEPARATOR));
		else
			name = key;
		int pos;
		if ((pos = name.indexOf(RubyMixin.INSTANCE_SUFFIX)) != -1)
			name = name.substring(0, pos);
		return name;
	}

	public IType[] getSourceTypes() {
		List<IType> result = new ArrayList<IType>();
		IMixinElement mixinElement = model.getRawModel().get(key);
		Object[] allObjects = mixinElement.getAllObjects();
		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info == null)
				continue;
			if (info.getKind() == RubyMixinElementInfo.K_CLASS
					|| info.getKind() == RubyMixinElementInfo.K_MODULE) {
				if (info.getObject() != null)
					result.add((IType) info.getObject());
			}
		}
		return result.toArray(new IType[result.size()]);
	}

	public RubyMixinClass getSuperclass() {
		IMixinElement mixinElement = model.getRawModel().get(key);
		if (mixinElement == null)
			return null;
		Object[] allObjects = mixinElement.getAllObjects();
		// IType type = null;
		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info == null) {
				continue;
			}
			// if (info.getKind() == RubyMixinElementInfo.K_CLASS) {
			// type = (IType) info.getObject();
			// if (type == null)
			// continue;
			// String key = RubyModelUtils.evaluateSuperClass(type);
			// if (key == null)
			// continue;
			// if (!this.isMeta())
			// key = key + RubyMixin.INSTANCE_SUFFIX;
			// RubyMixinClass s = (RubyMixinClass) model.createRubyElement(key);
			// return s;
			// }
			if (info.getKind() == RubyMixinElementInfo.K_SUPER) {
				SuperclassReferenceInfo sinfo = (SuperclassReferenceInfo) info
						.getObject();
				BasicContext c = new BasicContext(sinfo.getModule(), sinfo
						.getDecl());
				ExpressionTypeGoal g = new ExpressionTypeGoal(c, sinfo
						.getNode());
				DLTKTypeInferenceEngine engine = new DLTKTypeInferenceEngine();
				IEvaluatedType type2 = engine.evaluateType(g, 500);
				if (type2 instanceof RubyClassType) {
					RubyClassType rubyClassType = (RubyClassType) type2;
					String key = rubyClassType.getModelKey();
					if (!this.isMeta())
						key = key + RubyMixin.INSTANCE_SUFFIX;
					return (RubyMixinClass) model.createRubyElement(key);
				}
			}
		}
		String key;
		Set<String> includeSet = new HashSet<String>();
		RubyMixinClass[] includedClasses = model.createRubyClass(
				new RubyClassType("Object%")).getIncluded(); //$NON-NLS-1$
		for (int cnt = 0, max = includedClasses.length; cnt < max; cnt++) {
			includeSet.add(includedClasses[cnt].getKey());
		}
		if (this.isMeta())
			if (this.isModule())
				key = "Module%"; //$NON-NLS-1$
			else
				key = "Class%"; //$NON-NLS-1$
		else if (isModule()
				&& ("Kernel%".equals(this.key) || includeSet.contains(this.getKey()))) //$NON-NLS-1$
			return null;
		else
			key = "Object"; //$NON-NLS-1$
		if (!this.isMeta())
			key = key + RubyMixin.INSTANCE_SUFFIX;
		RubyMixinClass s = (RubyMixinClass) model.createRubyElement(key);
		return s;
	}

	/**
	 * Returns modules included into this class.
	 * 
	 * @return
	 */
	public RubyMixinClass[] getIncluded() {
		IMixinElement mixinElement = model.getRawModel().get(key);
		if (mixinElement == null)
			return new RubyMixinClass[0];
		List<RubyMixinClass> result = new ArrayList<RubyMixinClass>();
		HashSet<String> names = new HashSet<String>();
		Object[] allObjects = mixinElement.getAllObjects();
		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info == null) {
				continue;
			}
			if (info.getKind() == RubyMixinElementInfo.K_INCLUDE) {
				String inclKey = (String) info.getObject();
				if (names.add(inclKey)) {
					if (/* !this.isMeta() && */!inclKey
							.endsWith(RubyMixin.INSTANCE_SUFFIX))
						inclKey += RubyMixin.INSTANCE_SUFFIX;
					IRubyMixinElement element = model
							.createRubyElement(inclKey);
					// TODO if element is not found - try to use different path
					// combinations
					if (element instanceof RubyMixinClass)
						result.add((RubyMixinClass) element);
				}
			}
		}
		return result.toArray(new RubyMixinClass[result.size()]);
	}

	/**
	 * Returns modules this class is extended from.
	 * 
	 * @return
	 */
	public RubyMixinClass[] getExtended() {
		IMixinElement mixinElement = model.getRawModel().get(key);
		if (mixinElement == null)
			return new RubyMixinClass[0];
		List<RubyMixinClass> result = new ArrayList<RubyMixinClass>();
		HashSet<String> names = new HashSet<String>();
		Object[] allObjects = mixinElement.getAllObjects();
		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info == null) {
				continue;
			}
			if (info.getKind() == RubyMixinElementInfo.K_EXTEND) {
				String extKey = (String) info.getObject();
				if (names.add(extKey)) {
					if (/* !this.isMeta() && */!extKey
							.endsWith(RubyMixin.INSTANCE_SUFFIX))
						extKey += RubyMixin.INSTANCE_SUFFIX;
					IRubyMixinElement element = model.createRubyElement(extKey);
					if (element instanceof RubyMixinClass)
						result.add((RubyMixinClass) element);
				}
			}
		}
		return result.toArray(new RubyMixinClass[result.size()]);
	}

	public void findMethods(IMixinSearchPattern pattern,
			IMixinSearchRequestor requestor) {
		findMethods(pattern, requestor, new HashSet<String>());
	}

	protected void findMethods(IMixinSearchPattern pattern,
			IMixinSearchRequestor requestor, Set<String> processedKeys) {
		if (!processedKeys.add(key)) {
			return;
		}
		IMixinElement mixinElement = model.getRawModel().get(key);
		if (mixinElement == null)
			return;
		final IMixinElement[] children = mixinElement.getChildren();
		for (int i = 0; i < children.length; i++) {
			final IMixinElement child = children[i];
			if (pattern.evaluate(child.getLastKeySegment())) {
				IRubyMixinElement element = model.createRubyElement(child);
				if (element instanceof RubyMixinMethod) {
					requestor.acceptResult(element);
				} else if (element instanceof RubyMixinAlias) {
					RubyMixinAlias alias = (RubyMixinAlias) element;
					IRubyMixinElement oldElement = alias.getOldElement();
					if (oldElement instanceof RubyMixinMethod) {
						AliasedRubyMixinMethod a = new AliasedRubyMixinMethod(
								model, alias);
						requestor.acceptResult(a);
					}
				}
			}
		}

		RubyMixinClass[] included = this.getIncluded();
		for (int i = 0; i < included.length; i++) {
			included[i].findMethods(pattern, requestor, processedKeys);
		}

		RubyMixinClass[] extended = this.getExtended();
		for (int i = 0; i < extended.length; i++) {
			extended[i].findMethods(pattern, requestor, processedKeys);
		}

		if (!this.key.endsWith(RubyMixin.VIRTUAL_SUFFIX)) {
			RubyMixinClass superclass = getSuperclass();
			if (superclass != null) {

				if (!superclass.getKey().equals(key)) {
					superclass.findMethods(pattern, requestor, processedKeys);
				}
			}
		} else {
			String stdKey = this.key.substring(0, key.length()
					- RubyMixin.VIRTUAL_SUFFIX.length());
			IRubyMixinElement realElement = model.createRubyElement(stdKey);
			if (realElement instanceof RubyMixinClass) {
				RubyMixinClass rubyMixinClass = (RubyMixinClass) realElement;
				rubyMixinClass.findMethods(pattern, requestor, processedKeys);
			}
		}

	}

	public RubyMixinMethod[] findMethods(IMixinSearchPattern pattern) {
		final List<RubyMixinMethod> result = new ArrayList<RubyMixinMethod>();
		this.findMethods(pattern, new IMixinSearchRequestor() {
			final Set<String> names = new HashSet<String>();

			@Override
			public void acceptResult(IRubyMixinElement element) {
				if (element instanceof RubyMixinMethod) {
					RubyMixinMethod method = (RubyMixinMethod) element;
					if (names.add(method.getName())) {
						result.add(method);
					}
				}
			}

		}, new HashSet<String>());
		return result.toArray(new RubyMixinMethod[result.size()]);
	}

	public RubyMixinMethod getMethod(String name) {
		return getMethod(name, new HashSet<String>());
	}

	protected RubyMixinMethod getMethod(String name, Set<String> processedKeys) {
		if (!processedKeys.add(key)) {
			return null;
		}
		String possibleKey = key + MixinModel.SEPARATOR + name;
		IMixinElement mixinElement = model.getRawModel().get(possibleKey);
		if (mixinElement != null) {
			IRubyMixinElement element = model.createRubyElement(mixinElement);
			if (element instanceof RubyMixinMethod) {
				return (RubyMixinMethod) element;
			}
			if (element instanceof RubyMixinAlias) {
				RubyMixinAlias alias = (RubyMixinAlias) element;
				IRubyMixinElement oldElement = alias.getOldElement();
				if (oldElement instanceof RubyMixinMethod) {
					return new AliasedRubyMixinMethod(model, alias);
				}
			}
		}

		RubyMixinClass[] included = this.getIncluded();
		for (int i = 0; i < included.length; i++) {
			if (!this.key.equals(included[i].key)) {
				RubyMixinMethod method = included[i].getMethod(name,
						processedKeys);
				if (method != null)
					return method;
			}
		}

		RubyMixinClass[] extended = this.getExtended();
		for (int i = 0; i < extended.length; i++) {
			RubyMixinMethod method = extended[i].getMethod(name, processedKeys);
			if (method != null)
				return method;
		}

		// search superclass
		// if (!this.key.equals("Object") && !this.key.equals("Object%")) {
		RubyMixinClass superclass = getSuperclass();
		if (superclass != null) {
			if (superclass.getKey().equals(key))
				return null;
			return superclass.getMethod(name, processedKeys);
		}
		// }
		return null;
	}

	public RubyMixinClass[] getClasses() {
		List<RubyMixinClass> result = new ArrayList<RubyMixinClass>();
		IMixinElement mixinElement = model.getRawModel().get(key);
		IMixinElement[] children = mixinElement.getChildren();
		for (int i = 0; i < children.length; i++) {
			IRubyMixinElement element = model.createRubyElement(children[i]);
			if (element instanceof RubyMixinClass)
				result.add((RubyMixinClass) element);
		}
		return result.toArray(new RubyMixinClass[result.size()]);
	}

	public RubyMixinVariable[] getFields() {
		List<RubyMixinVariable> result = new ArrayList<RubyMixinVariable>();
		IMixinElement mixinElement = model.getRawModel().get(key);
		IMixinElement[] children = mixinElement.getChildren();
		for (int i = 0; i < children.length; i++) {
			IRubyMixinElement element = model.createRubyElement(children[i]);
			if (element instanceof RubyMixinVariable)
				result.add((RubyMixinVariable) element);
		}
		RubyMixinClass superclass = getSuperclass();
		if (superclass != null && superclass.key != "Object" //$NON-NLS-1$
				&& superclass.key != "Object%") { //$NON-NLS-1$
			if (superclass.getKey().equals(key))
				return null;
			RubyMixinVariable[] superFields = superclass.getFields();
			result.addAll(Arrays.asList(superFields));
		}

		return result.toArray(new RubyMixinVariable[result.size()]);
	}

}
