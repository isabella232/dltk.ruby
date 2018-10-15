/*******************************************************************************
 * Copyright (c) 2017 Red Hat Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.dltk.ruby.ui.tests;
import org.eclipse.dltk.ruby.ui.tests.folding.RubyCommentElementTests;
import org.eclipse.dltk.ruby.ui.tests.folding.RubyFoldingTest;
import org.eclipse.dltk.ruby.ui.tests.indenting.RubyAutoIndentStrategyTest;
import org.eclipse.dltk.ruby.ui.tests.search.MixinCompleteTests;
import org.eclipse.dltk.ruby.ui.tests.search.ThreadedUIMixinTests;
import org.eclipse.dltk.ruby.ui.tests.text.PartitioningTest;
import org.eclipse.dltk.ruby.ui.tests.text.RubyRequireHyperlinkDetectorTest;
import org.eclipse.dltk.ruby.ui.tests.text.indenting.IndentingTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		RubyAutoIndentStrategyTest.class, PartitioningTest.class, IndentingTest.class, RubyFoldingTest.class,
		MixinCompleteTests.class, ThreadedUIMixinTests.class, RubyRequireHyperlinkDetectorTest.class,
		RubyCommentElementTests.class
})
public class AllTests {

}
