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

import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IInstanceContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class SelfReferenceEvaluator extends RubyMixinGoalEvaluator {

	private IEvaluatedType result;

	public SelfReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	private ExpressionTypeGoal getTypedGoal() {
		return (ExpressionTypeGoal) goal;
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
		ISourceModuleContext typedContext = getTypedContext();
		if (typedContext instanceof IInstanceContext) {
			result = ((IInstanceContext) typedContext).getInstanceType();
		} else {
			ExpressionTypeGoal typedGoal = getTypedGoal();
			result = RubyTypeInferencingUtils.determineSelfClass(mixinModel,
					typedContext.getSourceModule(), typedContext.getRootNode(),
					typedGoal.getExpression().sourceStart());
		}
		// TODO: check if static self type is a descendent of the type from
		// InstanceContext (and use the descendent in this case)
		// Assert.isTrue(result != null);
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
