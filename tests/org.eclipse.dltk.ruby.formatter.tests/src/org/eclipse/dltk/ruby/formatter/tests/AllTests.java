/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ParserTest.class, SimpleTests.class,
		ClassesAndMethodsTest.class, ModulesTest.class, BlocksTest.class,
		RDocTest.class, IfTest.class, BeginTest.class, DoTest.class,
		CommentsTest.class, UnaryPlusTest.class, HereDocumentTest.class,
		StringsTest.class, RegexpTest.class, RubyDoc1Test.class,
		RubyDoc2Test.class, FormatRubyLibTest.class })
public class AllTests {
}
