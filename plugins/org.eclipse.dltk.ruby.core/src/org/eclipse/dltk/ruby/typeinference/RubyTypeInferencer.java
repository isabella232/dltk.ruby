/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.typeinference;

import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.DefaultTypeInferencer;
import org.eclipse.dltk.ti.EvaluatorStatistics;
import org.eclipse.dltk.ti.IPruner;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class RubyTypeInferencer extends DefaultTypeInferencer {

	private class SimplestRubyPruner implements IPruner {

		private long timeStart;
		private final long timeLimit;

		public SimplestRubyPruner(long timeLimit) {
			super();
			this.timeLimit = timeLimit;
		}

		@Override
		public void init() {
			this.timeStart = System.currentTimeMillis();
		}

		@Override
		public boolean prune(IGoal goal, EvaluatorStatistics stat) {
			long currentTime = System.currentTimeMillis();
			if (timeLimit != -1 && currentTime - timeStart > timeLimit)
				return true;
			if (stat != null) {
				if (stat.getSubGoalsDoneSuccessful() > 5)
					return true;
			}
			return false;
		}

	}

	public RubyTypeInferencer() {
		super(new RubyEvaluatorFactory());
	}

	@Override
	public synchronized IEvaluatedType evaluateType(AbstractTypeGoal goal,
			int timeLimit) {
		IEvaluatedType type = super.evaluateType(goal, new SimplestRubyPruner(
				timeLimit));
		if (type == null || type instanceof UnknownType) {
			// All things in ruby are instances of Object
			type = new RubyClassType("Object"); //$NON-NLS-1$
		}
		return type;
	}

	@Override
	public synchronized Object evaluateGoal(IGoal goal, IPruner pruner) {
		return super.evaluateGoal(goal, pruner);
	}

}
