/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference.evaluators;

import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinElementInfo;
import org.eclipse.dltk.ruby.typeinference.ConstantTypeGoal;
import org.eclipse.dltk.ruby.typeinference.DefaultRubyEvaluatorFactory;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ruby.typeinference.goals.NonTypeConstantTypeGoal;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class ConstantReferenceEvaluator extends RubyMixinGoalEvaluator {

	private IEvaluatedType result;

	private IGoal helperGoal;

	public ConstantReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	private ConstantTypeGoal getTypedGoal() {
		return (ConstantTypeGoal) DefaultRubyEvaluatorFactory
				.translateGoal(goal);
	}

	private ISourceModuleContext getTypedContext() {
		return (ISourceModuleContext) goal.getContext();
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] init() {
		helperGoal = null;
		ISourceModuleContext typedContext = getTypedContext();
		ConstantTypeGoal typedGoal = getTypedGoal();
		String constantName = typedGoal.getName();
		int calculationOffset = typedGoal.getOffset();

		String elementKey = RubyTypeInferencingUtils.searchConstantElement(
				mixinModel.getRawModel(), typedContext.getRootNode(),
				calculationOffset, constantName);

		IMixinElement constantElement = null;

		if (elementKey != null)
			constantElement = mixinModel.getRawModel().get(elementKey);

		if (constantElement == null)
			return IGoal.NO_GOALS;

		Object[] realObjs = constantElement.getAllObjects();
		for (int i = 0; i < realObjs.length; i++) {
			RubyMixinElementInfo realObj = (RubyMixinElementInfo) realObjs[i];
			if (realObj == null)
				continue;
			if (realObj.getKind() == RubyMixinElementInfo.K_CLASS
					|| realObj.getKind() == RubyMixinElementInfo.K_MODULE) {
				result = new RubyClassType(constantElement.getKey());
				break;
			} else if (realObj.getKind() == RubyMixinElementInfo.K_VARIABLE) {
				// String parent = constantElement.getParent().getKey();
				// String name = constantElement.getLastKeySegment();
				// helperGoal = new VariableTypeGoal (goal.getContext(), name,
				// parent, RubyVariableKind.CONSTANT);
				// Object[] allObjects = constantElement.getAllObjects();
				helperGoal = new NonTypeConstantTypeGoal(goal.getContext(),
						constantElement);
				break;
			}
		}
		if (helperGoal != null) {
			return new IGoal[] { helperGoal };
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

}
