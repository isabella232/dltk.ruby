/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.dltk.debug.ui.launchConfigurations.InterpreterTab;
import org.eclipse.dltk.debug.ui.launchConfigurations.ScriptArgumentsTab;

public class RubyTestingTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		RubyTestingMainLaunchConfigurationTab main = new RubyTestingMainLaunchConfigurationTab(
				mode);
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { main,
				new ScriptArgumentsTab(), new InterpreterTab(main),
				new EnvironmentTab(), new RubyTestingCommonTab() };
		setTabs(tabs);
	}

}
