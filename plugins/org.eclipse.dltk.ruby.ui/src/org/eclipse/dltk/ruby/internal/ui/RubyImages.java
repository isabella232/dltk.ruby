/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ui.PluginImagesHelper;
import org.eclipse.jface.resource.ImageDescriptor;

public class RubyImages {
	private static final PluginImagesHelper helper = new PluginImagesHelper(
			RubyUI.getDefault().getBundle(), new Path("/icons")); //$NON-NLS-1$

	public static final ImageDescriptor DESC_OBJ_RUBY_FILE = helper
			.createUnManaged(PluginImagesHelper.T_OBJ, "ruby_obj.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_WIZBAN_PROJECT_CREATION = helper
			.createUnManaged(PluginImagesHelper.T_WIZBAN,
					"projectcreate_wiz.png"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_WIZBAN_FILE_CREATION = helper
			.createUnManaged(PluginImagesHelper.T_WIZBAN, "filecreate_wiz.png"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_OVR_STATIC_FIELD = helper
			.createUnManaged(PluginImagesHelper.T_OVR, "static.png"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_OVR_CONST_FIELD = helper
			.createUnManaged(PluginImagesHelper.T_OVR, "const.png"); //$NON-NLS-1$
}
