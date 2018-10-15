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

import org.eclipse.dltk.ast.expressions.BooleanLiteral;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class BooleanLiteralEvaluator extends GoalEvaluator {

	public BooleanLiteralEvaluator(IGoal goal) {
		super(goal);
	}

	public IGoal produceNextSubgoal(IGoal previousGoal, Object previousResult) {
		return null;
	}

	@Override
	public Object produceResult() {
		ExpressionTypeGoal tg = (ExpressionTypeGoal) goal;
		BooleanLiteral l = (BooleanLiteral) tg.getExpression();
		if (l.boolValue())
			return new RubyClassType("TrueClass%"); //$NON-NLS-1$
		else
			return new RubyClassType("FalseClass%");// TrueClass || FalseClass //$NON-NLS-1$
	}

	@Override
	public IGoal[] init() {
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
