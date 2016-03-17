/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
