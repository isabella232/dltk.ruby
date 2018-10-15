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

import java.util.HashMap;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinElementInfo;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ruby.typeinference.goals.NonTypeConstantTypeGoal;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;

public class NonTypeConstantTypeEvaluator extends GoalEvaluator {

	private HashMap<String, RubyClassType> hardcoredTypes = new HashMap<String, RubyClassType>();
	private Object result;

	public NonTypeConstantTypeEvaluator(IGoal goal) {
		super(goal);
		hardcoredTypes.put("FALSE", new RubyClassType("FalseClass%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("RUBY_RELEASE_DATE", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("STDERR", new RubyClassType("IO%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("MatchingData", new RubyClassType("Class%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("TOPLEVEL_BINDING", new RubyClassType("Binding%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("ENV", new RubyClassType("Object%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("STDIN", new RubyClassType("IO%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("TRUE", new RubyClassType("TrueClass%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("RELEASE_DATE", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("NIL", new RubyClassType("NilClass%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("RUBY_PLATFORM", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("RUBY_VERSION", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("STDOUT", new RubyClassType("IO%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("PLATFORM", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("ARGV", new RubyClassType("Array%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("ARGF", new RubyClassType("Object%")); //$NON-NLS-1$ //$NON-NLS-2$
		hardcoredTypes.put("VERSION", new RubyClassType("String%")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private NonTypeConstantTypeGoal getTypedGoal() {
		return (NonTypeConstantTypeGoal) this.goal;
	}

	@Override
	public IGoal[] init() {
		NonTypeConstantTypeGoal g = getTypedGoal();
		IMixinElement element = g.getElement();
		Object[] allObjects = element.getAllObjects();
		if (allObjects == null)
			return IGoal.NO_GOALS;

		for (int i = 0; i < allObjects.length; i++) {
			RubyMixinElementInfo info = (RubyMixinElementInfo) allObjects[i];
			if (info != null
					&& info.getKind() == RubyMixinElementInfo.K_VARIABLE) {
				if (info.getObject() instanceof IField) {
					IField field = (IField) info.getObject();
					String name = field.getElementName();
					if (hardcoredTypes.containsKey(name)) {
						this.result = hardcoredTypes.get(name);
						break;
					}
				}
			}
		}

		return IGoal.NO_GOALS;
	}

	@Override
	public Object produceResult() {
		return result;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return IGoal.NO_GOALS;
	}

}
