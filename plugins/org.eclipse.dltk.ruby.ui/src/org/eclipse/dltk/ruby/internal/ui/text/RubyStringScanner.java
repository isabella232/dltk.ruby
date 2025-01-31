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

import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordPatternRule;

public class RubyStringScanner extends AbstractScriptScanner {

	private static final String[] fgTokenProperties = new String[] {
			IRubyColorConstants.RUBY_STRING, IRubyColorConstants.RUBY_DEFAULT,
			IRubyColorConstants.RUBY_CLASS_VARIABLE,
			IRubyColorConstants.RUBY_GLOBAL_VARIABLE,
			IRubyColorConstants.RUBY_INSTANCE_VARIABLE };

	public RubyStringScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store);

		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		List<IRule> rules = new ArrayList<>();

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new RubyWhitespaceDetector()));
		rules.add(new WordPatternRule(new RubyWordDetector(), "#$", "", //$NON-NLS-1$ //$NON-NLS-2$
				getToken(IRubyColorConstants.RUBY_GLOBAL_VARIABLE)));
		rules.add(new WordPatternRule(new RubyWordDetector(), "#@@", "", //$NON-NLS-1$ //$NON-NLS-2$
				getToken(IRubyColorConstants.RUBY_CLASS_VARIABLE)));
		rules.add(new WordPatternRule(new RubyWordDetector(), "#@", "", //$NON-NLS-1$ //$NON-NLS-2$
				getToken(IRubyColorConstants.RUBY_INSTANCE_VARIABLE)));

		setDefaultReturnToken(getToken(IRubyColorConstants.RUBY_STRING));

		return rules;
	}
}
