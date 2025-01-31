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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyDVarExpression;
import org.eclipse.dltk.ruby.ast.RubyMethodArgument;
import org.eclipse.dltk.ruby.ast.RubySingletonMethodDeclaration;
import org.eclipse.dltk.ruby.ast.RubyVariableKind;
import org.eclipse.dltk.ruby.internal.parsers.jruby.ASTUtils;
import org.eclipse.dltk.ruby.typeinference.IArgumentsContext;
import org.eclipse.dltk.ruby.typeinference.LocalVariableInfo;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ruby.typeinference.RubyMethodReference;
import org.eclipse.dltk.ruby.typeinference.RubyTypeInferencingUtils;
import org.eclipse.dltk.ruby.typeinference.VariableTypeGoal;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.ItemReference;
import org.eclipse.dltk.ti.goals.MethodCallsGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class VariableReferenceEvaluator extends RubyMixinGoalEvaluator {

	private LocalVariableInfo info;
	private MethodCallsGoal callsGoal = null;

	private List results = new ArrayList();
	private MethodDeclaration methodDeclaration;

	public VariableReferenceEvaluator(IGoal goal) {
		super(goal);
	}

	@Override
	public Object produceResult() {
		return RubyTypeInferencingUtils.combineTypes(results);
	}

	private String determineEnclosingMethod(ISourceModule module,
			ModuleDeclaration decl, VariableReference ref) {
		RubyClassType selfClass;
		ASTNode[] wayToNode = ASTUtils.restoreWayToNode(decl, ref);
		for (int i = wayToNode.length - 1; i >= 0; i--) {
			if (wayToNode[i] instanceof MethodDeclaration) {
				methodDeclaration = (MethodDeclaration) wayToNode[i];
				String name = methodDeclaration.getName();
				if (wayToNode[i] instanceof ModuleDeclaration
						&& !(methodDeclaration instanceof RubySingletonMethodDeclaration)) {
					return name;
				} else {
					selfClass = RubyTypeInferencingUtils.determineSelfClass(
							mixinModel, module, decl, ref.sourceStart());
					if (selfClass == null)
						return null;
				}
				return selfClass.getModelKey() + MixinModel.SEPARATOR + name;
			}
		}
		return null;
	}

	@Override
	public IGoal[] init() {
		VariableReference ref = getGoalVariableReference();
		if (ref.getVariableKind() == RubyVariableKind.LOCAL) {
			IContext context = goal.getContext();
			ModuleDeclaration rootNode = ((ISourceModuleContext) context)
					.getRootNode();
			VariableReference expression = ref;
			String varName = expression.getName().trim();
			if (context instanceof IArgumentsContext) {
				IArgumentsContext argumentsContext = (IArgumentsContext) context;
				IEvaluatedType argumentType = argumentsContext
						.getArgumentType(varName);
				if (argumentType != null) {
					results.add(argumentType);
					return IGoal.NO_GOALS;
				}
			}

			info = RubyTypeInferencingUtils.inspectLocalVariable(rootNode,
					expression.sourceStart(), varName);

			List<IGoal> poss = new ArrayList<IGoal>();

			if (info != null) {
				if (info.getLastAssignment() != null
						&& info.getLastAssignment().getRight() != null) {
					IGoal subgoal = new ExpressionTypeGoal(context, info
							.getLastAssignment().getRight());
					poss.add(subgoal);
				}
				for (int i = 0; i < info.getConditionalAssignments().length; i++) {
					if (info.getConditionalAssignments()[i].getRight() != null) {
						IGoal subgoal = new ExpressionTypeGoal(context, info
								.getConditionalAssignments()[i].getRight());
						poss.add(subgoal);
					}
				}
			}

			if (poss.size() == 0) {
				String key = null;
				if (context instanceof ISourceModuleContext) {
					ISourceModuleContext basicContext = (ISourceModuleContext) context;
					key = determineEnclosingMethod(basicContext
							.getSourceModule(), basicContext.getRootNode(), ref);
				}
				if (key != null) {
					int argPos = determineArgumentPos(methodDeclaration, ref
							.getName());
					if (argPos != -1) {
						int lastCurly = key.lastIndexOf(MixinModel.SEPARATOR);
						String parent = null;
						if (lastCurly != -1) {
							parent = key.substring(0, lastCurly);
						}
						String name = key.substring(lastCurly + 1);
						callsGoal = new MethodCallsGoal(context, name, parent);
						return new IGoal[] { callsGoal };
					}
				}
			}

			return poss.toArray(new IGoal[poss.size()]);

		} else {
			IEvaluatedType selfClass = RubyTypeInferencingUtils
					.determineSelfClass(mixinModel, goal.getContext(), ref
							.sourceStart());
			if (selfClass instanceof RubyClassType) {
				String selfKey = ((RubyClassType) selfClass).getModelKey();
				return new IGoal[] { new VariableTypeGoal(goal.getContext(),
						ref.getName(), selfKey, ref.getVariableKind()) };
			} else if (selfClass instanceof AmbiguousType) {
				AmbiguousType ambiType = (AmbiguousType) selfClass;
				List<IGoal> goalList = new ArrayList<IGoal>();
				IEvaluatedType[] possibleTypes = ambiType.getPossibleTypes();
				for (int cnt = 0, max = possibleTypes.length; cnt < max; cnt++) {
					if (possibleTypes[cnt] instanceof RubyClassType) {
						String selfKey = ((RubyClassType) possibleTypes[cnt])
								.getModelKey();
						goalList.add(new VariableTypeGoal(goal.getContext(),
								ref.getName(), selfKey, ref.getVariableKind()));
					}
				}
				return goalList.toArray(new IGoal[goalList.size()]);
			}
		}
		return IGoal.NO_GOALS;
	}

	private int determineArgumentPos(MethodDeclaration decl, String varName) {
		List methodArgs = methodDeclaration.getArguments();

		int pos = 0;
		int argPos = -1;
		for (Iterator<ASTNode> iterator = methodArgs.iterator(); iterator.hasNext();) {
			ASTNode marg = iterator.next();
			if (marg instanceof RubyMethodArgument) {
				RubyMethodArgument rubyMethodArgument = (RubyMethodArgument) marg;
				if (rubyMethodArgument.getName().equals(varName)) {
					argPos = pos;
					break;
				}
			}
			pos++;
		}
		return argPos;
	}

	private VariableReference getGoalVariableReference() {
		ASTNode expression = ((ExpressionTypeGoal) goal).getExpression();
		if (expression instanceof VariableReference)
			return (VariableReference) expression;
		if (expression instanceof RubyDVarExpression) {
			RubyDVarExpression dvar = (RubyDVarExpression) expression;
			return new VariableReference(dvar.sourceStart(), dvar.sourceEnd(),
					dvar.getName(), RubyVariableKind.LOCAL);
		}
		return null;
	}

	private ASTNode getArgFromCall(CallExpression expr) {
		VariableReference ref = getGoalVariableReference();
		if (ref.getVariableKind() != RubyVariableKind.LOCAL)
			return null;

		String name = ref.getName();

		int argPos = determineArgumentPos(methodDeclaration, name);

		if (argPos != -1) {

			CallArgumentsList args = expr.getArgs();
			if (args != null) {
				List<ASTNode> list = args.getChilds();
				if (argPos < list.size()) {
					ASTNode st = list.get(argPos);
					if (st instanceof RubyCallArgument) {
						RubyCallArgument rubyCallArgument = (RubyCallArgument) st;
						st = rubyCallArgument.getValue();
					}
					return st;
				}
			}

		}

		return null;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		if (subgoal == callsGoal) {
			List<IGoal> possibles = new ArrayList<IGoal>();
			if (result != null) {
				ItemReference[] refs = (ItemReference[]) result;
				for (int i = 0; i < refs.length; i++) { // TODO: for performance
					// reasons, sort them
					// somehow or leave only one
					CallExpression node = ((RubyMethodReference) refs[i])
							.getNode();
					if (node != null) {
						ASTNode arg = getArgFromCall(node);
						if (arg != null) {
							IResource resource = refs[i].getPosition()
									.getResource();
							ISourceModule module = (ISourceModule) DLTKCore
									.create(resource);
							if (module == null)
								continue;
							ModuleDeclaration decl = ASTUtils.getAST(module);
							if (decl == null)
								continue;
							BasicContext callContext = new BasicContext(module,
									decl);
							ExpressionTypeGoal g = new ExpressionTypeGoal(
									callContext, arg);
							possibles.add(g);
						}
					}
				}
			}
			return possibles.toArray(new IGoal[possibles.size()]);
		} else if ((result != null) && !(result instanceof UnknownType))
			results.add(result);
		return IGoal.NO_GOALS;
	}

}
