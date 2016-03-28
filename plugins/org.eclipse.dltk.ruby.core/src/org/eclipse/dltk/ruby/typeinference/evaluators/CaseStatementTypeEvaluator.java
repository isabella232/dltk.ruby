/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference.evaluators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyCaseStatement;
import org.eclipse.dltk.ruby.ast.RubyWhenStatement;
import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class CaseStatementTypeEvaluator extends GoalEvaluator {

	private List types = new ArrayList();

	public CaseStatementTypeEvaluator(IGoal goal) {
		super(goal);
	}

	private ExpressionTypeGoal getTypedGoal() {
		return (ExpressionTypeGoal) getGoal();
	}

	@Override
	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = this.getTypedGoal();
		ASTNode expression = typedGoal.getExpression();
		if (!(expression instanceof RubyCaseStatement))
			return IGoal.NO_GOALS;
		RubyCaseStatement caseSt = (RubyCaseStatement) expression;
		List<IGoal> subgoals = new ArrayList<IGoal>();
		List<RubyWhenStatement> whens = caseSt.getWhens();
		for (Iterator<RubyWhenStatement> iterator = whens.iterator(); iterator.hasNext();) {
			RubyWhenStatement when = iterator.next();
			ASTNode body = when.getBody();
			subgoals.add(new ExpressionTypeGoal(this.goal.getContext(), body));
		}
		return subgoals.toArray(new IGoal[subgoals.size()]);
	}

	@Override
	public Object produceResult() {
		return RubyTypeInferencingUtils.combineTypes(types);
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (result != null)
			this.types.add(result);
		return IGoal.NO_GOALS;
	}

}
