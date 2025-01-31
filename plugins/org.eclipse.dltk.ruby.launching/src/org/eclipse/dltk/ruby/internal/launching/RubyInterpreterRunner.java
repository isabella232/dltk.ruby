/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.launching;

import org.eclipse.dltk.launching.AbstractInterpreterRunner;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.ruby.launching.RubyLaunchConfigurationConstants;

public class RubyInterpreterRunner extends AbstractInterpreterRunner {
	public RubyInterpreterRunner(IInterpreterInstall install) {
		super(install);
	}

	@Override
	protected String getProcessType() {
		return RubyLaunchConfigurationConstants.ID_RUBY_PROCESS_TYPE;
	}
}
