/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.activestatedebugger;

import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterRunner;
import org.eclipse.dltk.launching.IInterpreterRunnerFactory;

/**
 * Factory responsible for creating instances of ActiveState's ruby debugging
 * engine.
 */
public class RubyActiveStateDebuggerRunnerFactory implements
		IInterpreterRunnerFactory {

	@Override
	public IInterpreterRunner createRunner(IInterpreterInstall install) {
		return new RubyActiveStateDebuggerRunner(install);
	}
}
