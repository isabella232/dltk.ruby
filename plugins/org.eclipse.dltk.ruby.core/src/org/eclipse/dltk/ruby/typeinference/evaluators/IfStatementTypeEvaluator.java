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

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;
import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class IfStatementTypeEvaluator extends GoalEvaluator {

	private final static int STATE_TRY_THEN = 0;

	private final static int STATE_WAITING_THEN = 1;

	private final static int STATE_TRY_ELSE = 2;

	private final static int STATE_WAITING_ELSE = 3;

	private final static int STATE_DONE = -2;

	private int state = STATE_TRY_THEN;

	private IEvaluatedType[] evaluatedTypes = new IEvaluatedType[2];

	private int index = STATE_TRY_THEN;

	public IfStatementTypeEvaluator(ExpressionTypeGoal goal) {
		super(goal);
	}

	private IGoal produceNextSubgoal(IGoal previousGoal, Object previousResult) {
		if (state == STATE_TRY_THEN) {
			ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
			RubyIfStatement expression = (RubyIfStatement) typedGoal
					.getExpression();
			ASTNode clause = expression.getThen();
			if (clause == null)
				state = STATE_TRY_ELSE;
			else {
				state = STATE_WAITING_THEN;
				return new ExpressionTypeGoal(goal.getContext(), clause);
			}
		}
		if (state == STATE_WAITING_THEN || state == STATE_WAITING_ELSE) {
			if (previousResult != null)
				evaluatedTypes[index++] = (IEvaluatedType) previousResult;
			state = (state == STATE_WAITING_THEN ? STATE_TRY_ELSE : STATE_DONE);
		} else {
			Assert.isTrue(previousGoal == null);
		}
		if (state == STATE_TRY_ELSE) {
			ExpressionTypeGoal typedGoal = (ExpressionTypeGoal) goal;
			RubyIfStatement expression = (RubyIfStatement) typedGoal
					.getExpression();
			ASTNode clause = expression.getElse();
			if (clause == null)
				state = STATE_DONE;
			else {
				state = STATE_WAITING_ELSE;
				return new ExpressionTypeGoal(goal.getContext(), clause);
			}
		}
		return null;
	}

	@Override
	public Object produceResult() {
		return RubyTypeInferencingUtils.combineTypes(evaluatedTypes);
	}

	@Override
	public IGoal[] init() {
		IGoal goal = produceNextSubgoal(null, null);
		if (goal != null)
			return new IGoal[] { goal };
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		IGoal goal = produceNextSubgoal(subgoal, result);
		if (goal != null)
			return new IGoal[] { goal };
		return IGoal.NO_GOALS;
	}

}
