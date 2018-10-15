/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.jruby.lexer.yacc;

import java.io.IOException;

import org.jruby.parser.Tokens;

public class HeredocEndTerm extends StrTerm {

	private final ISourcePosition position;

	/**
	 * @param position
	 */
	public HeredocEndTerm(ISourcePosition position) {
		this.position = position;
	}

	public int parseString(RubyYaccLexer lexer, LexerSource src)
			throws IOException {
		lexer.setValue(new Token("\"", position));
		return Tokens.tSTRING_END;
	}

}
