/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.ui.text.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;

public class SourceModuleLookup {

	/**
	 * Lookups {@link ISourceModule}s with the specified
	 * <code>modulePath</code>. Returns the array of the matched modules.
	 *
	 * @param project
	 *            project to lookup
	 * @param modulePath
	 *            the module file name
	 * @param suffix
	 *            optional file name suffix (<code>null</code> if not needed)
	 * @return
	 * @throws ModelException
	 */
	public static ISourceModule[] lookup(IScriptProject project,
			String modulePath, String suffix) throws ModelException {
		final List<ISourceModule> result = new ArrayList<>();
		final IPath path = new Path(modulePath);
		final String fileName = path.lastSegment();
		final String folderPath = path.removeLastSegments(1).toString();
		final IScriptFolder[] folders = project.getScriptFolders();
		for (int i = 0; i < folders.length; ++i) {
			final IScriptFolder folder = folders[i];
			if (folderPath.length() == 0
					|| folder.getElementName().endsWith(folderPath)) {
				final ISourceModule[] modules = folder.getSourceModules();
				for (int j = 0; j < modules.length; ++j) {
					final ISourceModule module = modules[j];
					final String moduleName = module.getElementName();
					if (fileName.equals(moduleName)) {
						result.add(module);
					} else if (suffix != null
							&& moduleName.length() == fileName.length()
									+ suffix.length()
							&& moduleName.startsWith(fileName)
							&& moduleName.endsWith(suffix)) {
						result.add(module);
					}
				}
			}
		}
		return result.toArray(new ISourceModule[result.size()]);
	}

}
