/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.ui.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.dltk.ruby.internal.ui.RubyUILanguageToolkit;
import org.eclipse.dltk.ui.compare.ScriptMergeViewer;
import org.eclipse.swt.widgets.Composite;

public class RubyMergeViewer extends ScriptMergeViewer {

	public RubyMergeViewer(Composite parent, CompareConfiguration configuration) {
		super(parent, configuration, RubyUILanguageToolkit.getInstance());
	}

}
