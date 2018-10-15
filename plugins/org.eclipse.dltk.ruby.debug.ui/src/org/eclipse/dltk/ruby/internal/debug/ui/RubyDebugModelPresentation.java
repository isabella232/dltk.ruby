/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.debug.ui;

import org.eclipse.dltk.debug.core.model.IScriptValue;
import org.eclipse.dltk.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ruby.internal.ui.editor.RubyEditor;
import org.eclipse.ui.IEditorInput;

public class RubyDebugModelPresentation extends ScriptDebugModelPresentation {

	@Override
	public String getEditorId(IEditorInput input, Object element) {
		String editorId = EditorUtility.getEditorID(input, element);
		if (editorId == null)
			editorId = RubyEditor.EDITOR_ID;

		return editorId;
	}

	@Override
	public String getDetailPaneText(IScriptValue value) {
		return value.getRawValue();
	}
}
