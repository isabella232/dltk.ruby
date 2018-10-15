/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.debug.ui.interpreters;

import org.eclipse.dltk.internal.debug.ui.interpreters.ScriptInterpreterPreferencePage;
import org.eclipse.dltk.internal.debug.ui.interpreters.InterpretersBlock;

public class RubyInterpreterPreferencePage extends ScriptInterpreterPreferencePage {
	
	public static final String PAGE_ID = "org.eclipse.dltk.ruby.preferences.interpreters"; //$NON-NLS-1$

	@Override
	public InterpretersBlock createInterpretersBlock() {
		return new RubyInterpretersBlock();
	}
}
