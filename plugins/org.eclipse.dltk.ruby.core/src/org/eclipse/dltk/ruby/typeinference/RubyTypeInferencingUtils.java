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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.core.search.TypeNameMatchRequestor;
import org.eclipse.dltk.core.search.indexing.IIndexConstants;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyBlock;
import org.eclipse.dltk.ruby.ast.RubyDAssgnExpression;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;
import org.eclipse.dltk.ruby.ast.RubyMethodArgument;
import org.eclipse.dltk.ruby.ast.RubyUnlessStatement;
import org.eclipse.dltk.ruby.ast.RubyUntilStatement;
import org.eclipse.dltk.ruby.ast.RubyWhileStatement;
import org.eclipse.dltk.ruby.core.RubyPlugin;
import org.eclipse.dltk.ruby.internal.parser.mixin.IRubyMixinElement;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinBuildVisitor;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinClass;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinMethod;
import org.eclipse.dltk.ruby.internal.parser.mixin.RubyMixinModel;
import org.eclipse.dltk.ruby.internal.parsers.jruby.ASTUtils;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.IInstanceContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.dltk.ti.types.RecursionTypeCall;

public class RubyTypeInferencingUtils {

	/**
	 * Searches all top level types, which starts with prefix
	 */
	public static IType[] getAllTypes(ISourceModule module, String prefix) {
		final List<IType> types = new ArrayList<IType>();

		TypeNameMatchRequestor requestor = new TypeNameMatchRequestor() {

			@Override
			public void acceptTypeNameMatch(TypeNameMatch match) {
				IType type = match.getType();
				if (type.getParent() instanceof ISourceModule) {
					types.add(type);
				}
			}

		};

		ScriptModelUtil.searchTypeDeclarations(module.getScriptProject(),
				prefix + "*", requestor); //$NON-NLS-1$

		return types.toArray(new IType[types.size()]);
	}

	public static ASTNode[] getAllStaticScopes(ModuleDeclaration rootNode,
			final int requestedOffset) {
		final Collection<ASTNode> scopes = new ArrayList<ASTNode>();
		ASTVisitor visitor = new OffsetTargetedASTVisitor(requestedOffset) {
			@Override
			public boolean visitInteresting(MethodDeclaration s) {
				scopes.add(s);
				return true;
			}

			@Override
			public boolean visitInteresting(ModuleDeclaration s) {
				scopes.add(s);
				return true;
			}

			@Override
			public boolean visitInteresting(TypeDeclaration s) {
				scopes.add(s);
				return true;
			}

			@Override
			protected boolean visitInteresting(RubyBlock b) {
				scopes.add(b);
				return true;
			}

			@Override
			protected boolean visitGeneralInteresting(ASTNode s) {
				if (ASTUtils.isNodeScoping(s)) {
					scopes.add(s);
					return true;
				}
				return super.visitGeneralInteresting(s);
			}

		};
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			RubyPlugin.log(e);
		}
		if (scopes.size() == 0)
			scopes.add(rootNode);
		return scopes.toArray(new ASTNode[scopes.size()]);
	}

	public static IMixinElement[] getModelStaticScopes(MixinModel model,
			ModuleDeclaration rootNode, final int requestedOffset) {
		String[] modelStaticScopesKeys = getModelStaticScopesKeys(model,
				rootNode, requestedOffset);
		IMixinElement[] result = new IMixinElement[modelStaticScopesKeys.length];
		for (int i = 1; i < modelStaticScopesKeys.length; i++) { //XXX-fourdman:
			// removed
			// Object
			// resulution
			result[i] = model.get(modelStaticScopesKeys[i]);
			// if (result[i] == null)
			// throw new RuntimeException("getModelStaticScopes(): Failed to get
			// element from mixin-model: " + modelStaticScopesKeys[i]);
		}
		return result;
	}

	public static String[] getModelStaticScopesKeys(MixinModel model,
			ModuleDeclaration rootNode, final int requestedOffset) {
		ASTNode[] allStaticScopes = RubyTypeInferencingUtils
				.getAllStaticScopes(rootNode, requestedOffset);
		return RubyMixinBuildVisitor.restoreScopesByNodes(allStaticScopes);
	}

	public static IEvaluatedType determineSelfClass(RubyMixinModel mixinModel,
			IContext context, int keyOffset) {
		if (context instanceof IInstanceContext) {
			IInstanceContext instanceContext = (IInstanceContext) context;
			return instanceContext.getInstanceType();
		} else {
			ISourceModuleContext basicContext = (ISourceModuleContext) context;
			return determineSelfClass(mixinModel, basicContext
					.getSourceModule(), basicContext.getRootNode(), keyOffset);
		}
	}

	/**
	 * Determines a fully-qualified names of the class scope that the given
	 * offset is statically enclosed in.
	 * 
	 * @param sourceModule
	 *            a module containing the given offsets
	 * @param rootNode
	 *            the root of AST corresponding to the given source module
	 * @param keyOffset
	 *            the offset
	 * @return The type of <code>self</code> at the given offset (never null)
	 */
	public static RubyClassType determineSelfClass(RubyMixinModel rubyModel,
			final ISourceModule sourceModule, ModuleDeclaration rootNode,
			final int keyOffset) {
		String[] keys = getModelStaticScopesKeys(rubyModel.getRawModel(),
				rootNode, keyOffset);
		if (keys != null && keys.length > 0) {
			String inner = keys[keys.length - 1];
			IRubyMixinElement rubyElement = rubyModel.createRubyElement(inner);
			// ssanders: Fallback to locating the immediate parent
			if ((rubyElement == null)
					&& (inner.indexOf(IIndexConstants.SEPARATOR) != -1)) {
				rubyElement = rubyModel.createRubyElement(inner.substring(0,
						inner.lastIndexOf(IIndexConstants.SEPARATOR)));
			}
			if (rubyElement instanceof RubyMixinMethod) {
				RubyMixinMethod method = (RubyMixinMethod) rubyElement;
				RubyMixinClass selfType = method.getSelfType();
				if (selfType != null) {
					return new RubyClassType(selfType.getKey());
				}
			} else if (rubyElement instanceof RubyMixinClass) {
				RubyMixinClass rubyMixinClass = (RubyMixinClass) rubyElement;
				return new RubyClassType(rubyMixinClass.getKey());
			}
		}
		return null;
	}

	public static RubyAssignment[] findLocalVariableAssignments(
			final ASTNode scope, final ASTNode nextScope, final String varName) {
		final Collection<RubyAssignment> assignments = new ArrayList<RubyAssignment>();
		ASTVisitor visitor = new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration s) throws Exception {
				if (s == scope)
					return true;
				return false;
			}

			@Override
			public boolean visit(TypeDeclaration s) throws Exception {
				if (s == scope)
					return true;
				return false;
			}

			@Override
			public boolean visit(ASTNode node) throws Exception {
				if (node instanceof RubyAssignment) {
					RubyAssignment assignment = (RubyAssignment) node;
					ASTNode lhs = assignment.getLeft();
					if (lhs instanceof VariableReference) {
						VariableReference varRef = (VariableReference) lhs;
						if (varName.equals(varRef.getName())) {
							assignments.add(assignment);
						}
					}
				}
				if (node == nextScope)
					return false;
				return true;
			}

		};
		try {
			scope.traverse(visitor);
		} catch (Exception e) {
			RubyPlugin.log(e);
		}
		return assignments.toArray(new RubyAssignment[assignments.size()]);
	}

	public static boolean isRootLocalScope(ASTNode node) {
		return node instanceof ModuleDeclaration
				|| node instanceof TypeDeclaration
				|| node instanceof MethodDeclaration;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluaedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(evaluaedTypes);
		types.remove(null);
		if (types.size() > 1 && types.contains(RecursionTypeCall.INSTANCE))
			types.remove(RecursionTypeCall.INSTANCE);
		return combineUniqueTypes(types.toArray(new IEvaluatedType[types.size()]));
	}

	private static IEvaluatedType combineUniqueTypes(IEvaluatedType[] types) {
		if (types.length == 0)
			return UnknownType.INSTANCE;
		if (types.length == 1)
			return types[0];
		return new AmbiguousType(types);
	}

	public static IEvaluatedType combineTypes(IEvaluatedType[] evaluaedTypes) {
		return combineTypes(Arrays.asList(evaluaedTypes));
	}

	public static ModuleDeclaration parseSource(ISourceModule module) {
		return SourceParserUtil.getModuleDeclaration(module);
	}

	public static IEvaluatedType getAmbiguousMetaType(IEvaluatedType receiver) {
		if (receiver instanceof AmbiguousType) {
			Set<IEvaluatedType> possibleReturns = new HashSet<IEvaluatedType>();
			AmbiguousType ambiguousType = (AmbiguousType) receiver;
			IEvaluatedType[] possibleTypes = ambiguousType.getPossibleTypes();
			for (int i = 0; i < possibleTypes.length; i++) {
				IEvaluatedType type = possibleTypes[i];
				IEvaluatedType possibleReturn = getAmbiguousMetaType(type);
				possibleReturns.add(possibleReturn);
			}
			return RubyTypeInferencingUtils.combineTypes(possibleReturns);
		}
		return null;
	}

	public static String searchConstantElement(MixinModel mixinModel,
			ModuleDeclaration module, int calculationOffset, String constantName) {
		String[] modelStaticScopes = getModelStaticScopesKeys(mixinModel,
				module, calculationOffset);

		String resultKey = null;

		for (int i = modelStaticScopes.length - 1; i >= 0; i--) {
			String possibleKey = modelStaticScopes[i]
					+ IMixinRequestor.MIXIN_NAME_SEPARATOR + constantName;
			if (mixinModel.keyExists(possibleKey)) {
				resultKey = possibleKey;
				break;
			}
		}

		// check top-most scope
		if (resultKey == null) {
			if (mixinModel.keyExists(constantName)) {
				resultKey = constantName;
			}
		}
		return resultKey;
	}

	private static class LocalVariablesSearchVisitor extends ASTVisitor {

		private class VariableAssignment {
			public RubyAssignment assignment;
			public int level;

			public VariableAssignment(RubyAssignment assignenment, int level) {
				super();
				this.assignment = assignenment;
				this.level = level;
			}

		}

		private List<VariableAssignment> assignements;
		private Stack<ASTNode> level = new Stack<ASTNode>();
		private int maxLevel;

		private final String name;
		private final int offset;
		private final ASTNode root;

		public LocalVariablesSearchVisitor(String name, ASTNode root, int offset) {
			super();
			this.name = name;
			this.root = root;
			this.offset = offset;
			this.maxLevel = 0;
			this.assignements = new ArrayList<VariableAssignment>();
			this.level.clear();
		}

		@Override
		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node == root)
				return true;
			if (node instanceof MethodDeclaration
					|| node instanceof TypeDeclaration)
				return false;
			if (node instanceof RubyAssignment) {
				if (!(node.sourceEnd() <= offset))
					return false;
				RubyAssignment rubyAssignment = (RubyAssignment) node;
				ASTNode lhs = rubyAssignment.getLeft();
				if (lhs instanceof VariableReference) {
					VariableReference varRef = (VariableReference) lhs;
					if (name.equals(varRef.getName())) {
						assignements.add(new VariableAssignment(rubyAssignment,
								level.size()));
					}
				}
			} else if (node instanceof RubyIfStatement
					|| node instanceof RubyForStatement2
					|| node instanceof RubyWhileStatement
					|| node instanceof RubyBlock
					|| node instanceof RubyUntilStatement
					|| node instanceof RubyUnlessStatement) {
				if (node.sourceStart() >= offset)
					return false;
				level.push(node);
				if (node.sourceEnd() >= offset && level.size() > maxLevel)
					maxLevel = level.size();
			}
			return true;
		}

		@Override
		public void endvisitGeneral(ASTNode node) throws Exception {
			if (level.size() > 0 && level.peek().equals(node)) {
				level.pop();
			}
		}

		public RubyAssignment getUnconditionalAssignment() {
			VariableAssignment[] array = assignements
					.toArray(new VariableAssignment[assignements.size()]);
			for (int i = array.length - 1; i >= 0; i--) {
				VariableAssignment a = array[i];
				if (a.level <= maxLevel) {
					return a.assignment;
				}
			}
			return null;
		}

		public List<RubyAssignment> getConditionals() {
			RubyAssignment unconditionalAssignment = getUnconditionalAssignment();
			List<RubyAssignment> result = new ArrayList<RubyAssignment>();
			for (Iterator<VariableAssignment> iter = assignements.iterator(); iter.hasNext();) {
				VariableAssignment assign = iter.next();
				if (unconditionalAssignment == null
						|| assign.assignment.sourceStart() > unconditionalAssignment
								.sourceStart())
					result.add(assign.assignment);

			}
			return result;

		}

	}

	private static RubyAssignment findAssignments(String variableName,
			ASTNode scopeNode, int tillOffset, List<RubyAssignment> conditionals) {
		LocalVariablesSearchVisitor visitor = new LocalVariablesSearchVisitor(
				variableName, scopeNode, tillOffset);
		try {
			scopeNode.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println();
		if (conditionals != null)
			conditionals.addAll(visitor.getConditionals());
		return visitor.getUnconditionalAssignment();
	}

	public static LocalVariableInfo inspectLocalVariable(
			ModuleDeclaration module, int offset, String name) {
		LocalVariableInfo info = new LocalVariableInfo();
		ASTNode[] scopes = getAllStaticScopes(module, offset);
		int i = -1;
		for (i = scopes.length - 1; i >= 0; i--) {
			ASTNode scope = scopes[i];
			if (scope instanceof TypeDeclaration) {
				info.setDeclaringScope(scope);
				List<RubyAssignment> conditionals = new ArrayList<RubyAssignment>();
				RubyAssignment last = findAssignments(name, scope, offset,
						conditionals);
				info.setLastAssignment(last);
				info.setDeclaringScope(scope);
				info.setConditionalAssignments(conditionals);
				break;
			} else if (scope instanceof MethodDeclaration) {
				MethodDeclaration method = (MethodDeclaration) scope;
				boolean isArgument = false;
				List<RubyMethodArgument> arguments = method.getArguments();
				for (Iterator<RubyMethodArgument> iterator = arguments.iterator(); iterator
						.hasNext();) {
					RubyMethodArgument arg = iterator.next();
					String argName = arg.getName();
					if (argName.equals(name)) {
						isArgument = true;
						break;
					}
				}
				if (isArgument
						&& (info.getKind() == LocalVariableInfo.KIND_DEFAULT))
					info.setKind(LocalVariableInfo.KIND_METHOD_ARG);
				List<RubyAssignment> conditionals = new ArrayList<RubyAssignment>();
				RubyAssignment last = findAssignments(name, scope, offset,
						conditionals);
				info.setLastAssignment(last);
				info.setDeclaringScope(scope);
				info.setConditionalAssignments(conditionals);
				break;
			} else if (scope instanceof RubyBlock) {
				boolean isArgument = false;
				RubyBlock block = (RubyBlock) scope;
				Set<ASTNode> vars = block.getVars();
				for (Iterator<ASTNode> iterator = vars.iterator(); iterator.hasNext();) {
					ASTNode vnode = iterator.next();
					if (vnode instanceof RubyDAssgnExpression) {
						RubyDAssgnExpression v = (RubyDAssgnExpression) vnode;
						if (v.getName().equals(name)) {
							isArgument = true;
							break;
						}
					}
				}
				if (isArgument) {
					if (info.getKind() == LocalVariableInfo.KIND_DEFAULT)
						info.setKind(LocalVariableInfo.KIND_BLOCK_ARG);
					// List conditionals = new ArrayList();
					// RubyAssignment last = findAssignments(name, scope,
					// offset,
					// conditionals);
					// info.setLastAssignment(last);
					// info.setDeclaringScope(scope);
					// info.setConditionalAssignments(conditionals);
					// break;
				}
			} else if (scope instanceof RubyForStatement2) {

				RubyForStatement2 forStatement = (RubyForStatement2) scope;
				ASTNode var = forStatement.getTarget();

				if (var instanceof RubyAssignment) {

					RubyAssignment rubyAssignment = (RubyAssignment) var;
					if (rubyAssignment.getLeft() instanceof VariableReference) {
						VariableReference ref = (VariableReference) rubyAssignment
								.getLeft();
						if (ref.getName().equals(name)) {
							if (info.getKind() == LocalVariableInfo.KIND_DEFAULT)
								info.setKind(LocalVariableInfo.KIND_LOOP_VAR);
							// List conditionals = new ArrayList();
							// RubyAssignment last = findAssignments(name,
							// scope,
							// offset, conditionals);
							// info.setLastAssignment(last);
							// info.setDeclaringScope(scope);
							// info.setConditionalAssignments(conditionals);
							// break;
						}
					}
				}

			}
		}
		if (i < 0) {
			// consider the whole module
			List<RubyAssignment> conditionals = new ArrayList<RubyAssignment>();
			RubyAssignment last = findAssignments(name, module, offset,
					conditionals);
			info.setLastAssignment(last);
			info.setDeclaringScope(module);
			info.setConditionalAssignments(conditionals);
		}
		return info;
	}

	// public static LocalVariableInfo searchLocalVars(ModuleDeclaration module,
	// int offset, String name) {
	// ASTNode[] scopes = getAllStaticScopes(module, offset);
	// int i = -1;
	// loop: for (i = scopes.length - 1; i >= 0; i--) {
	// if (scopes[i] instanceof MethodDeclaration
	// || scopes[i] instanceof TypeDeclaration) {
	// break;
	// } else if (scopes[i] instanceof RubyBlock) {
	// RubyBlock rubyBlock = (RubyBlock) scopes[i];
	// Set vars = rubyBlock.getVars();
	// for (Iterator iterator = vars.iterator(); iterator.hasNext();) {
	// ASTNode vnode = (ASTNode) iterator.next();
	// if (vnode instanceof RubyDAssgnExpression) {
	// RubyDAssgnExpression v = (RubyDAssgnExpression) vnode;
	// if (v.getName().equals(name)) {
	// break loop;
	// }
	// }
	// }
	// } else if (scopes[i] instanceof RubyForStatement2) {
	// RubyForStatement2 forst = (RubyForStatement2) scopes[i];
	// ASTListNode vars = forst.getList();
	// for (Iterator iterator = vars.getChilds().iterator(); iterator
	// .hasNext();) {
	// ASTNode vnode = (ASTNode) iterator.next();
	// if (vnode instanceof RubyAssignment) {
	// RubyAssignment assign = (RubyAssignment) vnode;
	// if (assign.getLeft() instanceof VariableReference) {
	// VariableReference ref = (VariableReference) assign
	// .getLeft();
	// if (ref.getName().equals(name))
	// break loop;
	// }
	// }
	// }
	// }
	// }
	// if (i < 0)
	// i = 0;
	// LocalVarSearchVisitor visitor = new LocalVarSearchVisitor(name,
	// scopes[i], offset);
	// try {
	// scopes[i].traverse(visitor);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// List conds = visitor.getConditionalAssignments();
	// RubyAssignment[] c = (RubyAssignment[]) conds
	// .toArray(new RubyAssignment[conds.size()]);
	// return new LocalVariableInfo(scopes[i], c, visitor.getLast());
	//
	// }

}
