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
package org.eclipse.dltk.ruby.formatter.lexer;

import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.RubyYaccLexer;
import org.jruby.lexer.yacc.Token;

public class HeredocToken extends Token {

	private final int func;

	/**
	 * @param value
	 * @param position
	 * @param func
	 */
	public HeredocToken(Object value, ISourcePosition position, int func) {
		super(value, position);
		this.func = func;
	}

	public boolean isIndent() {
		return (func & RubyYaccLexer.STR_FUNC_INDENT) != 0;
	}

}
