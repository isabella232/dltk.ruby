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
package org.eclipse.dltk.ruby.internal.core.codeassist;

import java.util.Comparator;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;

/**
 * This {@link Comparator} is used to sort type proposals in the following
 * order:
 * <ol>
 * <li>the specified module types should go first
 * <li>other project types should go next
 * <li>all other types should go last
 * </ol>
 */
public class ProjectTypeComparator implements Comparator<IType> {

	private final ISourceModule module;

	/**
	 * @param module
	 */
	public ProjectTypeComparator(ISourceModule module) {
		this.module = module;
	}

	@Override
	public int compare(IType type1, IType type2) {
		if (type1.getParent() instanceof ISourceModule
				&& type2.getParent() instanceof ISourceModule) {
			final ISourceModule module1 = (ISourceModule) type1.getParent();
			final ISourceModule module2 = (ISourceModule) type2.getParent();
			if (module1.isReadOnly() != module2.isReadOnly()) {
				return module1.isReadOnly() ? +1 : -1;
			}
			final boolean same1 = module.equals(module1);
			if (same1 != module.equals(module2)) {
				return same1 ? -1 : +1;
			}
		}
		return type1.getElementName().compareTo(type2.getElementName());
	}
}
