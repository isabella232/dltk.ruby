package org.eclipse.dltk.ruby.core.tests;

import org.eclipse.dltk.ruby.core.tests.assist.RubySelectionTests;
import org.eclipse.dltk.ruby.core.tests.parser.RubyParserRecoveryTests;
import org.eclipse.dltk.ruby.core.tests.parser.RubyParserTests;
import org.eclipse.dltk.ruby.core.tests.resources.RubyResourcesTests;
import org.eclipse.dltk.ruby.core.tests.search.RubyFullNameSearchTests;
import org.eclipse.dltk.ruby.core.tests.search.RubyMethodSearchTests;
import org.eclipse.dltk.ruby.core.tests.search.RubySearchTests;
import org.eclipse.dltk.ruby.core.tests.search.mixin.MixinModelManipulationTests;
import org.eclipse.dltk.ruby.core.tests.search.mixin.MixinProjectIsolationTests;
import org.eclipse.dltk.ruby.core.tests.search.mixin.MixinTestsSuite;
import org.eclipse.dltk.ruby.core.tests.search.mixin.RubyMixinClassTests;
import org.eclipse.dltk.ruby.core.tests.text.completion.RubyCompletionTests;
import org.eclipse.dltk.ruby.core.tests.typeinference.MethodsTest;
import org.eclipse.dltk.ruby.core.tests.typeinference.SimpleTest;
import org.eclipse.dltk.ruby.core.tests.typeinference.StatementsTest;
import org.eclipse.dltk.ruby.core.tests.typeinference.VariablesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ RubyResourcesTests.class, MixinTestsSuite.class, MixinModelManipulationTests.class,
		RubyMixinClassTests.class, RubySelectionTests.class, RubyCompletionTests.class, RubyParserTests.class,
		RubyParserRecoveryTests.class, VariablesTest.class, MethodsTest.class, StatementsTest.class, SimpleTest.class,
		MixinProjectIsolationTests.class, RubySearchTests.class, RubyFullNameSearchTests.class,
		RubyMethodSearchTests.class })
public class AllTests {

	// FIXME: fix running of this tests under mac os x
	// suite.addTest(StdlibRubyParserTests.suite());
	// suite.addTest(JRuby1RubyParserTests.suite());

	// XXX: uncomment this tests, when type hierarchies
	// support will be implemented
	// suite.addTest(TypeHierarchyTests.suite());

}
