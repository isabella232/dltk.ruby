/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.tests;

import java.util.Map;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.formatter.tests.AbstractFormatterTest;
import org.eclipse.dltk.ui.formatter.IScriptFormatter;

public abstract class AbstractRubyFormatterTest extends AbstractFormatterTest {

	@Override
	protected IScriptFormatter createFormatter(
			Map<String, Object> preferences) {
		return preferences != null ? new TestRubyFormatter(Util.LINE_SEPARATOR,
				preferences) : new TestRubyFormatter();
	}
}
