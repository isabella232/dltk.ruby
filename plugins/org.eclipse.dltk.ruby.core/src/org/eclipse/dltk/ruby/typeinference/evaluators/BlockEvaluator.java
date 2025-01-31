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

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class BlockEvaluator extends GoalEvaluator {

	private ASTNode lastStatement;
	private IEvaluatedType result;

	public BlockEvaluator(IGoal goal) {
		super(goal);
	}

	private ExpressionTypeGoal getTypedGoal() {
		return (ExpressionTypeGoal) this.getGoal();
	}

	@Override
	public Object produceResult() {
		return this.result;
	}

	@Override
	public IGoal[] init() {
		ExpressionTypeGoal typedGoal = getTypedGoal();
		Block block = (Block) typedGoal.getExpression();
		List<ASTNode> statements = block.getStatements();
		if (statements.size() > 0) {
			this.lastStatement = statements.get(statements.size() - 1);
			ExpressionTypeGoal subgoal = new ExpressionTypeGoal(goal
					.getContext(), this.lastStatement);
			return new IGoal[] { subgoal };
		}
		return IGoal.NO_GOALS;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		this.result = (IEvaluatedType) result;
		return IGoal.NO_GOALS;
	}

}
