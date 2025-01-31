/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ruby.internal.ui.text.rules.BeginOfLineRule;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptMultilineCommentScanner;
import org.eclipse.dltk.ui.text.TodoTaskPreferencesOnPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class RubyDocScanner extends ScriptMultilineCommentScanner {
	private static final String[] fgTokenProperties = new String[] {
			IRubyColorConstants.RUBY_DOC, IRubyColorConstants.RUBY_DOC_TOPIC,
			IRubyColorConstants.RUBY_TODO_COMMENT };

	public RubyDocScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store, IRubyColorConstants.RUBY_DOC,
				IRubyColorConstants.RUBY_TODO_COMMENT,
				new TodoTaskPreferencesOnPreferenceStore(store), false);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		final List<IRule> rules = new ArrayList<>();

		final IToken topic = getToken(IRubyColorConstants.RUBY_DOC_TOPIC);
		final IToken other = getToken(IRubyColorConstants.RUBY_DOC);

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new RubyWhitespaceDetector()));
		rules.add(new BeginOfLineRule(topic, '='));
		rules.add(createTodoRule());

		setDefaultReturnToken(other);
		return rules;
	}

}
