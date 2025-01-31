/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.internal.ui.editor.ScriptOutlinePage;
import org.eclipse.dltk.ruby.core.RubyLanguageToolkit;
import org.eclipse.dltk.ruby.internal.ui.RubyUI;
import org.eclipse.dltk.ruby.internal.ui.text.IRubyPartitions;
import org.eclipse.dltk.ruby.internal.ui.text.RubyPairMatcher;
import org.eclipse.dltk.ruby.internal.ui.text.folding.RubyFoldingStructureProvider;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.folding.IFoldingStructureProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;

public class RubyEditor extends ScriptEditor {
	public static final String EDITOR_ID = "org.eclipse.dltk.ruby.ui.editor.RubyEditor"; //$NON-NLS-1$

	public static final String EDITOR_CONTEXT = "#RubyEditorContext"; //$NON-NLS-1$

	public static final String RULER_CONTEXT = "#RubyRulerContext"; //$NON-NLS-1$

	private org.eclipse.dltk.internal.ui.editor.BracketInserter fBracketInserter = new RubyBracketInserter(
			this);

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId(EDITOR_CONTEXT);
		setRulerContextMenuId(RULER_CONTEXT);
	}

	@Override
	public IPreferenceStore getScriptPreferenceStore() {
		return RubyUI.getDefault().getPreferenceStore();
	}

	@Override
	public ScriptTextTools getTextTools() {
		return RubyUI.getDefault().getTextTools();
	}

	@Override
	protected ScriptOutlinePage doCreateOutlinePage() {
		return new RubyOutlinePage(this,
				RubyUI.getDefault().getPreferenceStore());
	}

	@Override
	protected void connectPartitioningToElement(IEditorInput input,
			IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension = (IDocumentExtension3) document;
			if (extension.getDocumentPartitioner(
					IRubyPartitions.RUBY_PARTITIONING) == null) {
				RubyDocumentSetupParticipant participant = new RubyDocumentSetupParticipant();
				participant.setup(document);
			}
		}
	}

	private IFoldingStructureProvider fFoldingProvider = null;

	@Override
	protected IFoldingStructureProvider createFoldingStructureProvider() {
		if (fFoldingProvider == null) {
			fFoldingProvider = new RubyFoldingStructureProvider();
		}
		return fFoldingProvider;
	}

	@Override
	public String getEditorId() {
		return EDITOR_ID;
	}

	@Override
	public IDLTKLanguageToolkit getLanguageToolkit() {
		return RubyLanguageToolkit.getDefault();
	}

	@Override
	public String getCallHierarchyID() {
		return "org.eclipse.dltk.callhierarchy.view"; //$NON-NLS-1$
	}

	@Override
	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(
				new String[] { "org.eclipse.dltk.ui.rubyEditorScope" }); //$NON-NLS-1$
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		boolean closeBrackets = true;
		boolean closeStrings = true;
		boolean closeAngularBrackets = false;

		fBracketInserter.setCloseBracketsEnabled(closeBrackets);
		fBracketInserter.setCloseStringsEnabled(closeStrings);
		fBracketInserter.setCloseAngularBracketsEnabled(closeAngularBrackets);

		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer instanceof ITextViewerExtension)
			((ITextViewerExtension) sourceViewer)
					.prependVerifyKeyListener(fBracketInserter);
	}

	private static ListenerList<IRubyEditorListener> doSetInputListeners = new ListenerList<>();

	public static void addListener(IRubyEditorListener listener) {
		doSetInputListeners.add(listener);
	}

	public static void removeListener(IRubyEditorListener listener) {
		doSetInputListeners.remove(listener);
	}

	protected void notifyDoSetInput(IModelElement element) {
		for (IRubyEditorListener listener : doSetInputListeners) {
			if (listener != null) {
				listener.notifyDoSetInput(element);
			}
		}
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		IModelElement element = getInputModelElement();
		if (element != null) {
			notifyDoSetInput(element);
		}
	}

	@Override
	public void dispose() {
		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer instanceof ITextViewerExtension)
			((ITextViewerExtension) sourceViewer)
					.removeVerifyKeyListener(fBracketInserter);
		super.dispose();
	}

	@Override
	protected ICharacterPairMatcher createBracketMatcher() {
		return new RubyPairMatcher();
	}

}
