/*******************************************************************************
 * Copyright (c) 2007, 2016 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package org.eclipse.dltk.ruby.debug.tests;

import org.eclipse.dltk.ruby.debug.tests.console.RubyFilenameLinenumberTests;
import org.eclipse.dltk.ruby.debug.tests.launching.RubyLaunchingTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RubyLaunchingTests.class, RubyFilenameLinenumberTests.class })
public class AllTests {
}
