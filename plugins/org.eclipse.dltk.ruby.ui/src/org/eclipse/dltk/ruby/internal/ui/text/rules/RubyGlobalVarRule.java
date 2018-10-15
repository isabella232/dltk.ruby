/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class RubyGlobalVarRule implements IPredicateRule {

	private final Token defaultToken;

	public RubyGlobalVarRule(Token defaultToken) {
		this.defaultToken = defaultToken;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	@Override
	public IToken getSuccessToken() {
		return defaultToken;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int read = scanner.read();
		if (read == '$') {
			read = scanner.read();
			if (read == '\'') {
				return defaultToken;
			}
			scanner.unread();
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

}
