/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.ui.actions;

import org.eclipse.dltk.ruby.internal.ui.RubyUILanguageToolkit;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.actions.OpenMethodAction;

public class RubyOpenMethodAction extends OpenMethodAction {

	@Override
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return RubyUILanguageToolkit.getInstance();
	}

}
