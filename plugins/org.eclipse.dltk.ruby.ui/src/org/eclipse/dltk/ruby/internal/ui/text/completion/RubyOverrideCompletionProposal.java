/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.completion;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.ScriptTypeCompletionProposal;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.contentassist.IContextInformation;


public class RubyOverrideCompletionProposal extends ScriptTypeCompletionProposal implements ICompletionProposalExtension4 {

	private String fMethodName;

	public RubyOverrideCompletionProposal(ISourceModule cu, String methodName, int start, int length, String displayName, String completionProposal) {
		super(completionProposal, cu, start, length, null, displayName, 0);
		Assert.isNotNull(methodName);
		Assert.isNotNull(cu);

		fMethodName= methodName;

		StringBuffer buffer= new StringBuffer();
		buffer.append(completionProposal);		
		
		setReplacementString(buffer.toString());		
	}

	@Override
	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		return fMethodName;
	}

	@Override
	protected boolean updateReplacementString(IDocument document, char trigger, int offset) throws CoreException, BadLocationException {
		final IDocument buffer= new Document(document.get());
		int index= offset - 1;
		while (index >= 0 && Character.isJavaIdentifierPart(buffer.getChar(index)))
			index--;
		final int length= offset - index - 1;
		buffer.replace(index + 1, length, " "); //$NON-NLS-1$
		return true;
	}
	
	@Override
	public boolean isAutoInsertable() {
		return false;
	}
	
	@Override
	public IContextInformation getContextInformation() {		
		return new ContextInformation(getDisplayString(), getDisplayString());
	}
}
