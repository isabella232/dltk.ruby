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
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class RubyDoubleQuoteStringRule implements IPredicateRule {

	protected static final int UNDEFINED = -1;
	protected IToken fToken;
	protected int fColumn = UNDEFINED;

	public RubyDoubleQuoteStringRule(IToken token) {
		Assert.isNotNull(token);
		fToken = token;
	}

	public void setColumnConstraint(int column) {
		if (column < 0)
			column = UNDEFINED;
		fColumn = column;
	}

	@Override
	public IToken getSuccessToken() {
		return fToken;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		boolean inExpression = false;
		int curlyDepth = 0;
		int c = scanner.read();
		int p = -1;
		int p2;

		if (c == '"') {
			if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
				IToken curToken = null;
				int count = 0;
				while (curToken == null && c != ICharacterScanner.EOF) {
					p2 = p;
					p = c;
					c = scanner.read();
					count++;

					if (p != '\\' || p2 == '\\') {
						if (c == '{') {
							if (p == '#')
								inExpression = true;
							if (inExpression)
								curlyDepth++;
						} else if (c == '}') {
							if (inExpression) {
								curlyDepth--;
								if (curlyDepth == 0)
									inExpression = false;
							}
						} else if (c == '"' && !inExpression)
							curToken = fToken;
					}
				}
				if (curToken != null) {
					return curToken;
				} else {
					while (count > 0) {
						scanner.unread();
						count--;
					}
				}
			}
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}

}
