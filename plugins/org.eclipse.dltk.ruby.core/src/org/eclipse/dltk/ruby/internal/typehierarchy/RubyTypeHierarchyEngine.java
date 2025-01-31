/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.typehierarchy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.TypeNameMatch;
import org.eclipse.dltk.core.search.TypeNameMatchRequestor;

public class RubyTypeHierarchyEngine {
	private static final boolean DEBUG = false;

	public static IType[] locateSuperTypes(IType type,
			IProgressMonitor monitor) {
		try {
			String[] superTypes = type.getSuperClasses();
			List<IType> types = new ArrayList<>();
			if (superTypes != null) {
				monitor.beginTask(
						Messages.RubyTypeHierarchyEngine_collectingTypes,
						superTypes.length);
				IDLTKSearchScope scope = SearchEngine.createWorkspaceScope(
						DLTKLanguageManager.getLanguageToolkit(type));
				for (int i = 0; i < superTypes.length; ++i) {
					if (DEBUG) {
						System.out.println("Type:" + type.getElementName() //$NON-NLS-1$
								+ " has supertype:" + superTypes[i]); //$NON-NLS-1$
					}
					TypeNameMatch[] possibleTypes = searchTypesForName(
							superTypes[i], monitor, scope);
					for (int j = 0; j < possibleTypes.length; ++i) {
						IType sType = possibleTypes[j].getType();
						if (sType.exists()
								&& filterTypeFromSelection(sType, type)) {
							types.add(sType);
						}
					}
				}
			}
			return types.toArray(new IType[types.size()]);
		} catch (ModelException e) {
			e.printStackTrace();
		} finally {
			monitor.done();
		}
		return null;
	}

	private static boolean filterTypeFromSelection(IType superType,
			IType type) {
		return true;
	}

	private static TypeNameMatch[] searchTypesForName(String name,
			IProgressMonitor monitor, IDLTKSearchScope scope) {
		SearchRequestor reqestor = new SearchRequestor();
		SearchEngine engine = new SearchEngine((WorkingCopyOwner) null);
		monitor.setTaskName(Messages.RubyTypeHierarchyEngine_searchingTypes);
		try {
			engine.searchAllTypeNames(null, 0, name.toCharArray(),
					SearchPattern.R_EXACT_MATCH
							| SearchPattern.R_CASE_SENSITIVE,
					IDLTKSearchConstants.TYPE, scope, reqestor,
					IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, monitor);
			return reqestor.getResult();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static class SearchRequestor extends TypeNameMatchRequestor {
		private volatile boolean fStop;

		private List<TypeNameMatch> fResult;

		public SearchRequestor() {
			super();
			fResult = new ArrayList<>(5);
		}

		public TypeNameMatch[] getResult() {
			return fResult.toArray(new TypeNameMatch[fResult.size()]);
		}

		public void cancel() {
			fStop = true;
		}

		@Override
		public void acceptTypeNameMatch(TypeNameMatch match) {
			if (fStop)
				return;
			fResult.add(match);
		}
	}
}
