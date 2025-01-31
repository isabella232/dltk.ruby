/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.text.completion;

import java.util.Vector;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.tests.model.AbstractModelCompletionTests;
import org.eclipse.dltk.core.tests.model.CompletionTestsRequestor;
import org.eclipse.dltk.ruby.core.tests.Activator;

import junit.framework.Test;

public class RubyCompletionTests extends AbstractModelCompletionTests {

	// private static final int RELEVANCE = (RelevanceConstants.R_DEFAULT
	// + RelevanceConstants.R_INTERESTING + RelevanceConstants.R_CASE +
	// RelevanceConstants.R_NON_RESTRICTED);

	public RubyCompletionTests(String name) {
		super(name);
	}

	@Override
	public void setUpSuite() throws Exception {
		// We need to initialie at least one interpreter.

		PROJECT = setUpScriptProject("completion", Activator.PLUGIN_ID);
		waitUntilIndexesReady();
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		waitForAutoBuild();
		// RubyPlugin.initialized = true;
		super.setUpSuite();
	}

	@Override
	public void tearDownSuite() throws Exception {
		deleteProject("completion");
		super.tearDownSuite();
	}

	public static Test suite() {
		return new Suite(RubyCompletionTests.class);
	}

	// private String makeResult(String[] elements, String[] completions, int[]
	// relevance) {
	// StringBuffer buffer = new StringBuffer();
	// for (int i = 0; i < elements.length; ++i) {
	// buffer.append("element:" + elements[i] + " completion:" +
	// completions[i]
	// + " relevance:" + relevance[i]);
	//
	// if (i != elements.length - 1) {
	// buffer.append("\n");
	// }
	// }
	// return buffer.toString();
	// }

	// private String makeResult(String[] elements) {
	// String[] completions = new String[elements.length];
	// int[] relevance = new int[elements.length];
	// for (int i = 0; i < elements.length; ++i) {
	// completions[i] = elements[i];
	// relevance[i] = RELEVANCE;
	// }
	// return makeResult(elements, completions, relevance);
	// }
	//
	// private String makeResult(String[] elements, int[] relevance) {
	// String[] completions = new String[elements.length];
	// for (int i = 0; i < elements.length; ++i) {
	// completions[i] = elements[i];
	// }
	// return makeResult(elements, completions, relevance);
	// }

	public void testCompletion001() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "new.rb");

		String str = cu.getSource();
		String completeBehind = "Foo.new.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion002() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "inner.rb");

		String str = cu.getSource();
		String completeBehind = "Foo42::";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion003() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c1.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion004() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c2.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion005() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c3.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion006() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c4.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion007() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "colon1.rb");

		String str = cu.getSource();
		String completeBehind = "::";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().indexOf("Mine42") != -1);
		assertTrue(requestor.getResults().indexOf("Mix42") != -1);

	}

	public void testCompletion008() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "colon2.rb");

		String str = cu.getSource();
		String completeBehind = "::";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().indexOf("Mine") != -1) && (requestor.getResults().indexOf("Mix") != -1));

	}

	public void testCompletion009() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "colon3.rb");

		String str = cu.getSource();
		String completeBehind = "Min";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().indexOf("Mine") != -1));

	}

	public void testCompletion010() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "var1.rb");

		String str = cu.getSource();
		String completeBehind = "@a";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion011() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "var2.rb");

		String str = cu.getSource();
		String completeBehind = "@a";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion012() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "var3.rb");

		String str = cu.getSource();
		String completeBehind = "@@a";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion013() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "const1.rb");

		String str = cu.getSource();
		String completeBehind = "Mega";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion014() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c5.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion015() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c6.rb");

		String str = cu.getSource();
		String completeBehind = "t.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion016() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c7.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion017() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "singl1.rb");

		String str = cu.getSource();
		String completeBehind = "Foo66.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().indexOf("cool") != -1);
		assertTrue(requestor.getResults().indexOf("cool2") != -1);

	}

	public void testCompletion018() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "singl2.rb");

		String str = cu.getSource();
		String completeBehind = "Foo66.cool2.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion019() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c8.rb");

		String str = cu.getSource();
		String completeBehind = "@categ";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion020() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c0.rb");

		String str = cu.getSource();
		String completeBehind = "::";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();

		cu.codeComplete(cursorLocation, requestor);
	}

	public void testCompletion021() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c67.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion022() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "object.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion023() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b181387.rb");

		String str = cu.getSource();
		String completeBehind = "t.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));

	}

	public void testCompletion024() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180869.rb");

		String str = cu.getSource();
		String completeBehind = ".x";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));
	}

	public void testCompletion025() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180152.rb");

		String str = cu.getSource();
		String completeBehind = "::x";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));
	}

	public void testCompletion026() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "c9.rb");

		String str = cu.getSource();
		String completeBehind = "fo";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().indexOf("foo") != -1);
	}

	public void testCompletion027() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "inside_block.rb");

		String str = cu.getSource();
		String completeBehind = "v.tu";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion028() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180149.rb");

		String str = cu.getSource();
		String completeBehind = "p";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion029() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180143.rb");

		String str = cu.getSource();
		String completeBehind = "arg3['sdsd'] = ";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion030() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180146.rb");

		String str = cu.getSource();
		String completeBehind = "ff g";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion031() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180160.rb");

		String str = cu.getSource();
		String completeBehind = "_f";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion032() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180157.rb");

		String str = cu.getSource();
		String completeBehind = "puts v";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion033() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180162_01.rb");

		String str = cu.getSource();
		String completeBehind = "@data[@i";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion034() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180162_02.rb");

		String str = cu.getSource();
		String completeBehind = "@data[@";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion035() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180163.rb");

		String str = cu.getSource();
		String completeBehind = "xx";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion036() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180165.rb");

		String str = cu.getSource();
		String completeBehind = "t";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion037() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180155.rb");

		String str = cu.getSource();
		String completeBehind = "Test.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	// check that keyword is "elsif", but not "elseif"
	public void testCompletion038Keywords() throws ModelException {

		ISourceModule cu = getSourceModule("completion", "src", "b180158.rb");

		String str = cu.getSource();
		String[] keyWords = str.split("\n");
		for (int i = 0; i < keyWords.length; i++) {
			keyWords[i] = keyWords[i].trim();
		}

		for (int i = 0; i < keyWords.length; i++) {
			CompletionTestsRequestor requestor = new CompletionTestsRequestor();
			String completeBehind = keyWords[i];
			int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();
			cursorLocation--;// try to complete one char before the end

			waitForAutoBuild();
			cu.codeComplete(cursorLocation, requestor);
			String completionResults = requestor.getResults();
			assertTrue(completionResults.length() > 0);
		}
	}

	public void testCompletion039() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180152.rb");

		String str = cu.getSource();
		String completeBehind = "::";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue((requestor.getResults().length() > 0));
	}

	public void testCompletion040() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b182532.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion041() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b182532.rb");

		String str = cu.getSource();
		String completeBehind = "y.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion042() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b191439.rb");

		String str = cu.getSource();
		String completeBehind = ".se";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int methodOccurence = completionResults.indexOf("send");
		assertTrue(methodOccurence > -1);
	}

	public void testCompletion043() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b186509.rb");

		String str = cu.getSource();
		String completeBehind = ".n";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int methodOccurence = completionResults.indexOf("new(i,g,d,s,a,r)");
		assertTrue(methodOccurence > -1);
	}

	public void testCompletion044() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b185650.rb");

		String str = cu.getSource();
		String completeBehind = "l.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();

		Vector<String> methods = new Vector<>();
		int lastElementsOccurance = 0;
		while ((lastElementsOccurance = completionResults.indexOf("element:", lastElementsOccurance)) > -1) {
			int lastElementsIndex = lastElementsOccurance + "element:".length();
			lastElementsOccurance = completionResults.indexOf(" ", lastElementsOccurance);
			methods.add(completionResults.substring(lastElementsIndex, lastElementsOccurance));
		}

		Vector<String> relevances = new Vector<>();
		int lastRelevanceOccurance = 0;
		while (lastRelevanceOccurance > -1) {
			lastRelevanceOccurance = completionResults.indexOf("relevance:", lastRelevanceOccurance);
			int lastRelevanceIndex = lastRelevanceOccurance + "relevance:".length();
			lastRelevanceOccurance = completionResults.indexOf("\n", lastRelevanceOccurance);
			if (lastRelevanceOccurance > -1)
				relevances.add(completionResults.substring(lastRelevanceIndex, lastRelevanceOccurance));
			else
				relevances.add(completionResults.substring(lastRelevanceIndex));
		}

		assertTrue(methods.size() == relevances.size());
	}

	public void testCompletion045() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180383.rb");

		String str = cu.getSource();
		String completeBehind = "File.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion046() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180383.rb");

		String str = cu.getSource();
		String completeBehind = "File.";
		int cursorLocation = str.indexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion047() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180382.rb");

		String str = cu.getSource();
		String completeBehind = "File.ex";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion048() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180372.rb");

		String str = cu.getSource();
		String completeBehind = "@f";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion049() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180854.rb");

		String str = cu.getSource();
		String completeBehind = "y.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion050() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b192388.rb");

		String str = cu.getSource();
		String completeBehind = "self.m";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion051() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180388_03.rb");

		String str = cu.getSource();
		String completeBehind = ".new; @parser.s";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion052() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b186510.rb");

		String str = cu.getSource();
		String completeBehind = "z2.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion053() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b183950.rb");

		String str = cu.getSource();
		String completeBehind = "t.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);

		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion054() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b185643_01.rb");

		String str = cu.getSource();
		String completeBehind = "val.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		if (completionResults != null) {
			assertTrue(completionResults.indexOf(">>") == -1);
			assertTrue(completionResults.indexOf("[]=") > -1);
		}
	}

	public void testCompletion055() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b185643_02.rb");

		String str = cu.getSource();
		String completeBehind = "val.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		if (completionResults != null) {
			assertTrue(completionResults.indexOf(">>") > -1);
			assertTrue(completionResults.indexOf("[]=") == -1);
		}
	}

	public void testCompletion056() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180397.rb");

		String str = cu.getSource();
		String completeBehind = "@body.i";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion057() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b198654.rb");

		String str = cu.getSource();
		String completeBehind = "4.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().indexOf("%") > -1);
	}

	public void testCompletion058() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b198704.rb");

		String str = cu.getSource();
		String completeBehind = "--1.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().indexOf("%") > -1);
	}

	public void testCompletion059() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b209354.rb");

		String str = cu.getSource();
		String completeBehind = "w.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().length() > 0);
	}

	public void testCompletion060_FAILS() throws ModelException {
		if (notYetImplemented())
			return;
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b193898.rb");

		String str = cu.getSource();
		String completeBehind = "as.methods.so";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		assertTrue(requestor.getResults().indexOf("sort") > -1);
	}

	public void testCompletion061_FAILS() throws ModelException {
		if (notYetImplemented())
			return;
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b194725.rb");

		String str = cu.getSource();
		String completeBehind = "__FILE_";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		int lineIndex = requestor.getResults().indexOf("__LINE__");
		assertTrue(lineIndex == -1);
	}

	public void REM_testCompletion062_FAILS_ON_WINDOWS() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b195463.rb");

		String str = cu.getSource();
		String completeBehind = "String.new.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		int resultsLength = requestor.getResults().length();
		assertTrue(resultsLength > 0);
	}

	public void testCompletion063() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b196781.rb");

		String str = cu.getSource();
		String completeBehind = "protecte";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		int resultsLength = requestor.getResults().length();
		assertTrue(resultsLength > 0);
	}

	public void testCompletion064_FAILS() throws ModelException {
		if (notYetImplemented())
			return;
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b198381.rb");

		String str = cu.getSource();
		String completeBehind = "med";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int methodIndex = completionResults.indexOf("medved(a,b,c,d)");
		assertTrue(methodIndex > -1);
	}

	/* It does NOT work in the editor */
	public void REM_testCompletion065_FAILS_IN_EDITOR() throws ModelException {
		if (notYetImplemented())
			return;
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180167.rb");

		String str = cu.getSource();
		String completeBehind = "x";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int resultsLength = completionResults.length();
		assertTrue(resultsLength > 0);
	}

	public void testCompletion066() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b180147.rb");

		String str = cu.getSource();
		String completeBehind = ".";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int resultsLength = completionResults.length();
		assertTrue(resultsLength > 0);
	}

	public void testCompletion067() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b194564.rb");

		String str = cu.getSource();
		String completeBehind = "3.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int resultsLength = completionResults.length();
		assertTrue(resultsLength > 0);
	}

	/* Test is not full, more examples of differences may be provided */
	public void testCompletion068() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "b191448.rb");

		String str = cu.getSource();
		String completeBehind = "str1.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults();
		int scanfIndex = completionResults.indexOf("scanf");
		assertTrue(scanfIndex == -1);
	}

	public void testCompletionAfterDot() throws ModelException {
		CompletionTestsRequestor requestor = new CompletionTestsRequestor();
		ISourceModule cu = getSourceModule("completion", "src", "afterDot.rb");

		String str = cu.getSource();
		String completeBehind = "x.";
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();

		waitForAutoBuild();
		cu.codeComplete(cursorLocation, requestor);
		String completionResults = requestor.getResults(false, false);
		assertTrue(completionResults.indexOf("method1inAfterDot") != -1);
		assertTrue(completionResults.indexOf("method2inAfterDot") != -1);
	}
}
