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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.BigNumericLiteral;
import org.eclipse.dltk.ast.expressions.BooleanLiteral;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.NumericLiteral;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ruby.ast.RubyArrayExpression;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyBacktickStringLiteral;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyCaseStatement;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;
import org.eclipse.dltk.ruby.ast.RubyConstantDeclaration;
import org.eclipse.dltk.ruby.ast.RubyDVarExpression;
import org.eclipse.dltk.ruby.ast.RubyDynamicBackquoteStringExpression;
import org.eclipse.dltk.ruby.ast.RubyDynamicStringExpression;
import org.eclipse.dltk.ruby.ast.RubyHashExpression;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;
import org.eclipse.dltk.ruby.ast.RubyRegexpExpression;
import org.eclipse.dltk.ruby.ast.RubySelfReference;
import org.eclipse.dltk.ruby.ast.RubySymbolReference;
import org.eclipse.dltk.ruby.typeinference.evaluators.AssignmentEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.BlockEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.BooleanLiteralEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.CaseStatementTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.ColonExpressionEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.ConstantReferenceEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.FieldParentKeyVerificator;
import org.eclipse.dltk.ruby.typeinference.evaluators.IfStatementTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.MethodCallTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.MethodCallVerificator;
import org.eclipse.dltk.ruby.typeinference.evaluators.MethodReturnTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.NonTypeConstantTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.RubyArgumentTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.RubyVariableTypeEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.SelfReferenceEvaluator;
import org.eclipse.dltk.ruby.typeinference.evaluators.VariableReferenceEvaluator;
import org.eclipse.dltk.ruby.typeinference.goals.ColonExpressionGoal;
import org.eclipse.dltk.ruby.typeinference.goals.NonTypeConstantTypeGoal;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.FieldPositionVerificationGoal;
import org.eclipse.dltk.ti.goals.FixedAnswerEvaluator;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.goals.MethodCallVerificationGoal;
import org.eclipse.dltk.ti.goals.MethodReturnTypeGoal;

public class DefaultRubyEvaluatorFactory implements IGoalEvaluatorFactory {

	private GoalEvaluator createExpressionEvaluator(ExpressionTypeGoal goal) {
		ASTNode expr = goal.getExpression();

		// literals
		if (expr instanceof RubyRegexpExpression)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Regexp%")); //$NON-NLS-1$
		if (expr instanceof RubyHashExpression)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Hash%")); //$NON-NLS-1$
		if (expr instanceof BigNumericLiteral)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Bignum%")); //$NON-NLS-1$
		if (expr instanceof NumericLiteral)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Fixnum%")); //$NON-NLS-1$
		if (expr instanceof StringLiteral)
			return new FixedAnswerEvaluator(goal, new RubyClassType("String%")); //$NON-NLS-1$
		if (expr instanceof RubyDynamicBackquoteStringExpression)
			return new FixedAnswerEvaluator(goal, new RubyClassType("String%")); //$NON-NLS-1$
		if (expr instanceof RubyDynamicStringExpression)
			return new FixedAnswerEvaluator(goal, new RubyClassType("String%")); //$NON-NLS-1$
		if (expr instanceof RubyBacktickStringLiteral)
			return new FixedAnswerEvaluator(goal, new RubyClassType("String%")); //$NON-NLS-1$
		if (expr instanceof RubyArrayExpression)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Array%")); //$NON-NLS-1$
		if (expr instanceof MethodDeclaration)
			return new FixedAnswerEvaluator(goal,
					new RubyClassType("NilClass%")); //$NON-NLS-1$
		if (expr instanceof RubySymbolReference)
			return new FixedAnswerEvaluator(goal, new RubyClassType("Symbol%")); //$NON-NLS-1$

		if (expr instanceof BooleanLiteral)
			return new BooleanLiteralEvaluator(goal);
		if (expr instanceof VariableReference
				|| expr instanceof RubyDVarExpression)
			return new VariableReferenceEvaluator(goal);

		if (expr instanceof RubyAssignment)
			return new AssignmentEvaluator(goal);
		if (expr instanceof ConstantReference)
			return new ConstantReferenceEvaluator(goal);
		if (expr instanceof RubySelfReference)
			return new SelfReferenceEvaluator(goal);
		if (expr instanceof CallExpression)
			return new MethodCallTypeEvaluator(goal);
		if (expr instanceof RubyIfStatement)
			return new IfStatementTypeEvaluator(goal);
		if (expr instanceof RubyCaseStatement)
			return new CaseStatementTypeEvaluator(goal);
		if (expr instanceof Block)
			return new BlockEvaluator(goal);
		if (expr instanceof RubyColonExpression)
			return new ColonExpressionEvaluator(goal);
		if (expr instanceof RubyConstantDeclaration)
			return new ConstantReferenceEvaluator(goal);
		if (expr instanceof RubyCallArgument)
			return new RubyArgumentTypeEvaluator(goal);

		// return new NullGoalEvaluator(goal);
		return null;
	}

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		if (goal instanceof FieldPositionVerificationGoal)
			return new FieldParentKeyVerificator(goal);

		if (goal instanceof MethodCallVerificationGoal)
			return new MethodCallVerificator(goal);

		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			return createExpressionEvaluator(exprGoal);
		} else if (goal instanceof ConstantTypeGoal)
			return new ConstantReferenceEvaluator(goal);
		else if (goal instanceof MethodReturnTypeGoal)
			return new MethodReturnTypeEvaluator(goal);
		else if (goal instanceof ColonExpressionGoal)
			return new ColonExpressionEvaluator(goal);
		else if (goal instanceof NonTypeConstantTypeGoal)
			return new NonTypeConstantTypeEvaluator(goal);
		else if (goal instanceof VariableTypeGoal) {
			return new RubyVariableTypeEvaluator(goal);
		}

		return null;
	}

	public static IGoal translateGoal(IGoal goal) {
		if (goal instanceof ExpressionTypeGoal) {
			ExpressionTypeGoal exprGoal = (ExpressionTypeGoal) goal;
			ASTNode expr = exprGoal.getExpression();
			IContext context = goal.getContext();
			if (expr instanceof ConstantReference) {
				ConstantReference reference = (ConstantReference) expr;
				return new ConstantTypeGoal(context, reference.sourceStart(),
						reference.getName());
			} else if (expr instanceof RubyConstantDeclaration) {
				SimpleReference reference = ((RubyConstantDeclaration) expr)
						.getName();
				// TODO: consider the constant's path
				return new ConstantTypeGoal(context, reference.sourceStart(),
						reference.getName());
			} else if (expr instanceof RubyColonExpression) {
				RubyColonExpression colonExpression = (RubyColonExpression) expr;
				return new ColonExpressionGoal(context, colonExpression);
			}
		}
		return goal;
	}

}
