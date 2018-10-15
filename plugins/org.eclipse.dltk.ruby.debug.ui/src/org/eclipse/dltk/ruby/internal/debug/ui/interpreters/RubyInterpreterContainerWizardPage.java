/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.debug.ui.interpreters;

import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterContainerWizardPage;
import org.eclipse.dltk.ruby.core.RubyNature;

public class RubyInterpreterContainerWizardPage extends
		AbstractInterpreterContainerWizardPage {

	@Override
	public String getScriptNature() {
		return RubyNature.NATURE_ID;
	}

}
