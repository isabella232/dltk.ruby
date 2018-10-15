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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core;

import java.util.List;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.compiler.task.TaskTagUtils;
import org.eclipse.dltk.compiler.task.TodoTask;
import org.eclipse.dltk.ruby.core.RubyPlugin;

public class RubyCorePreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		List<TodoTask> taskTags = TaskTagUtils.getDefaultTags();
		taskTags.add(new TodoTask("OPTIMIZE", TodoTask.PRIORITY_NORMAL));
		Preferences store = RubyPlugin.getDefault().getPluginPreferences();
		TaskTagUtils.initializeDefaultValues(store, taskTags);
	}
}
