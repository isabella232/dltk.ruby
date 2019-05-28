/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.typeinference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.core.tests.model.CompletionTestsRequestor2;
import org.eclipse.dltk.ruby.core.tests.Activator;

import junit.framework.ComparisonFailure;

public abstract class AbstractTypeInferencingTests extends AbstractModelTests {
	protected static IScriptProject PROJECT;

	protected class CompletionResult {
		public String proposals;
		public String context;
		public int cursorLocation;
		public int tokenStart;
		public int tokenEnd;
	}

	Hashtable<String, String> oldOptions;
	ISourceModule wc = null;

	private static final int COMPLETE_TIMEOUT = 5000;

	public AbstractTypeInferencingTests(String name) {
		super(name);
	}

	@Override
	public ISourceModule getWorkingCopy(String path, String source) throws ModelException {
		return super.getWorkingCopy(path, source, this.wcOwner, null);
	}

	protected CompletionResult complete(String path, String source, String completeBehind) throws ModelException {
		return this.complete(path, source, false, completeBehind);
	}

	protected CompletionResult complete(String path, String source, boolean showPositions, String completeBehind)
			throws ModelException {
		return this.complete(path, source, showPositions, completeBehind, null, null);
	}

	protected CompletionResult complete(String path, String source, boolean showPositions, String completeBehind,
			String tokenStartBehind, String token) throws ModelException {
		this.wc = getWorkingCopy(path, source);
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, showPositions);
		String str = this.wc.getSource();
		int cursorLocation = str.lastIndexOf(completeBehind) + completeBehind.length();
		int tokenStart = -1;
		int tokenEnd = -1;
		if (tokenStartBehind != null && token != null) {
			tokenStart = str.lastIndexOf(tokenStartBehind) + tokenStartBehind.length();
			tokenEnd = tokenStart + token.length() - 1;
		}
		this.wc.codeComplete(cursorLocation, requestor, this.wcOwner, COMPLETE_TIMEOUT);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		result.tokenStart = tokenStart;
		result.tokenEnd = tokenEnd;
		return result;
	}

	protected String loadContent(String path) throws IOException {
		StringBuffer buffer = new StringBuffer();
		try (InputStream input = Activator.openResource(path);) {
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(reader);
			char[] data = new char[100 * 1024]; // tests shouldnt be more that 100 kb
			int size = br.read(data);
			buffer.append(data, 0, size);
		}
		String content = buffer.toString();
		return content;
	}

	protected CompletionResult contextComplete(ISourceModule cu, int cursorLocation) throws ModelException {
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, false, false);
		cu.codeComplete(cursorLocation, requestor, this.wcOwner, COMPLETE_TIMEOUT);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		return result;
	}

	protected CompletionResult snippetContextComplete(IType type, String snippet, int insertion, int cursorLocation,
			boolean isStatic) throws ModelException {
		CompletionTestsRequestor2 requestor = new CompletionTestsRequestor2(true, false, false, false);
		type.codeComplete(snippet.toCharArray(), insertion, cursorLocation, null, null, null, isStatic, requestor,
				this.wcOwner);
		CompletionResult result = new CompletionResult();
		result.proposals = requestor.getResults();
		result.context = requestor.getContext();
		result.cursorLocation = cursorLocation;
		return result;
	}

	@Override
	public void setUpSuite() throws Exception {
		super.setUpSuite();
		this.oldOptions = DLTKCore.getOptions();
		// waitUntilIndexesReady();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wcOwner = new WorkingCopyOwner() {
		};
	}

	@Override
	public void tearDownSuite() throws Exception {
		DLTKCore.setOptions(this.oldOptions);
		this.oldOptions = null;
		super.tearDownSuite();
	}

	@Override
	protected void tearDown() throws Exception {
		if (this.wc != null) {
			this.wc.discardWorkingCopy();
			this.wc = null;
		}
		super.tearDown();
	}

	protected void assertResults(String expected, String actual) {
		try {
			assertEquals(expected, actual);
		} catch (ComparisonFailure c) {
			System.out.println(actual);
//			System.out.println();
			throw c;
		}
	}
}
