/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.completion;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ruby.core.model.FakeMethod;
import org.eclipse.dltk.ruby.internal.ui.RubyLabelProvider;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;

public class RubyCompletionProposalLabelProvider extends
		CompletionProposalLabelProvider {

	private static final String SEPARATOR = RubyLabelProvider.SEPARATOR;
	private static final String PACKAGE_SEPARATOR = RubyLabelProvider.PACKAGE_SEPARATOR;

	@Override
	protected String createMethodProposalLabel(CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')');

		IMethod method = (IMethod) methodProposal.getModelElement();
		nameBuffer.append(SEPARATOR);
		if (method instanceof FakeMethod
				&& ((FakeMethod) method).getReceiver() != null) {
			nameBuffer.append(((FakeMethod) method).getReceiver());
		} else {
			IModelElement parent = method.getParent();
			if (parent instanceof IType) {
				IType type = (IType) parent;
				nameBuffer.append(type.getTypeQualifiedName(PACKAGE_SEPARATOR));
			} else {
				nameBuffer.append(parent.getElementName());
			}
		}

		return nameBuffer.toString();
	}

	@Override
	protected String createOverrideMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer = new StringBuffer();

		// method name
		nameBuffer.append(methodProposal.getName());

		// parameters
		nameBuffer.append('(');
		appendParameterList(nameBuffer, methodProposal);
		nameBuffer.append(')');

		IMethod method = (IMethod) methodProposal.getModelElement();
		nameBuffer.append(SEPARATOR);
		if (method instanceof FakeMethod
				&& ((FakeMethod) method).getReceiver() != null) {
			String receiver = ((FakeMethod) method).getReceiver();
			nameBuffer.append(receiver);
		} else {
			IModelElement parent = method.getParent();
			if (parent instanceof IType) {
				IType type = (IType) parent;
				nameBuffer.append(type.getTypeQualifiedName(PACKAGE_SEPARATOR));
			} else {
				nameBuffer.append(parent.getElementName());
			}
		}

		return nameBuffer.toString();
	}

	@Override
	public String createTypeProposalLabel(CompletionProposal typeProposal) {
		final StringBuffer nameBuffer = new StringBuffer();
		final IType type = (IType) typeProposal.getModelElement();
		RubyLabelProvider.appendQualifiedType(type, nameBuffer);
		return nameBuffer.toString();
	}

}
