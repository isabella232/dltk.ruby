/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.actions;

import org.eclipse.dltk.ruby.internal.ui.RubyUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.actions.OpenTypeAction;

public class RubyOpenTypeAction extends OpenTypeAction {
	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return RubyUILanguageToolkit.getInstance();
	}

	@Override
	protected String getOpenTypeErrorMessage() {
		return Messages.RubyOpenTypeAction_anExceptionOccurredWhileOpeningTheClassModule;
	}

	@Override
	protected String getOpenTypeErrorTitle() {
		return Messages.RubyOpenTypeAction_openClassModule;
	}

	@Override
	protected String getOpenTypeDialogMessage() {
		return Messages.RubyOpenTypeAction_selectAClassModuleToOpen;
	}

	@Override
	protected String getOpenTypeDialogTitle() {
		return Messages.RubyOpenTypeAction_openClassModule;
	}
}
