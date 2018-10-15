/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ruby.internal.debug.ui.console.RubyFileHyperlink;
import org.eclipse.dltk.testing.DLTKTestingMessages;
import org.eclipse.jface.action.Action;

public class RubyOpenEditorAction extends Action {

	private final Object element;
	private int lineNumber;

	/**
	 * @param element
	 * @param lineNumber
	 */
	public RubyOpenEditorAction(Object element, int lineNumber) {
		super(DLTKTestingMessages.OpenEditorAction_action_label);
		this.element = element;
		this.lineNumber = lineNumber;
	}

	@Override
	public void run() {
		try {
			RubyFileHyperlink.openInEditor(element, lineNumber);
		} catch (CoreException e) {
			RubyTestingPlugin.error(Messages.openEditorError, e);
		}
	}

}
