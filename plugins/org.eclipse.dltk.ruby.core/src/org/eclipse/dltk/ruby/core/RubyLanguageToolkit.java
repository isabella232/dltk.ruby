/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core;

import org.eclipse.dltk.core.AbstractLanguageToolkit;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;

public class RubyLanguageToolkit extends AbstractLanguageToolkit {
	protected static RubyLanguageToolkit sToolkit = new RubyLanguageToolkit();
	public RubyLanguageToolkit() {
	}

	@Override
	public boolean languageSupportZIPBuildpath() {
		return false;
	}

	@Override
	public String getNatureId() {
		return RubyNature.NATURE_ID;
	}

	public static IDLTKLanguageToolkit getDefault() {
		return sToolkit;
	}

	@Override
	public String getLanguageName() {
		return "Ruby"; //$NON-NLS-1$
	}

	@Override
	public String getLanguageContentType() {
		return "org.eclipse.dltk.rubyContentType"; //$NON-NLS-1$
	}
	
	@Override
	public String getPreferenceQualifier() {
		return RubyPlugin.PLUGIN_ID;
	}
}
