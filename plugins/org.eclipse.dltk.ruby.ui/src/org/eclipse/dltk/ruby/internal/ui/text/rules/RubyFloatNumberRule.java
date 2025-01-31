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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * An implementation of <code>IRule</code> detecting a numerical value.
 */
public class RubyFloatNumberRule implements IRule {
	protected static final int UNDEFINED = -1;
	protected IToken fToken;
	protected int fColumn = UNDEFINED;

	public RubyFloatNumberRule(IToken token) {
		Assert.isNotNull(token);
		fToken = token;
	}

	public void setColumnConstraint(int column) {
		if (column < 0)
			column = UNDEFINED;
		fColumn = column;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		int p = c;
		if (Character.isDigit((char) c) || c == '.') {
			if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
				do {
					p = c;
					c = scanner.read();
				} while (Character.isDigit((char) c));
				if ((c != 'e' && c != 'E')) {
					scanner.unread();
				}				
				if (p == '.') {
					scanner.unread();
					return Token.UNDEFINED;
				}
				return fToken;
			}
		}
		scanner.unread();
		return Token.UNDEFINED;
	}
}
