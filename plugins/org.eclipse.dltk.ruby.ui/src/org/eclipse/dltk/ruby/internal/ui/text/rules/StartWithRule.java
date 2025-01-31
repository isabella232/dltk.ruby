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

import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class StartWithRule implements IRule
{
	private IToken token;
	private char[] start;
	private boolean noSameBefore = false;

	public StartWithRule( IToken token, char start ) {
		super();
		this.token = token;
		this.start = new char[]{ start };
	}
	public StartWithRule( IToken token, char[] start ) {
		super();
		this.token = token;
		this.start = start;
	}
	public StartWithRule( IToken token, char start, boolean nsb ) {
		super();
		this.token = token;
		this.start = new char[]{ start };
		this.noSameBefore = nsb;
	}
	protected char[] getAdditional() {
		return CharOperation.NO_CHAR;
	}

	boolean isInAddition(char c ) {
		char[] additional = getAdditional();
		for( int i = 0; i < additional.length; ++i ) {
			if( additional[i] == c ) {
				return true;
			}
		}
		return false;
	}
	@Override
	public IToken evaluate(ICharacterScanner scanner)
	{
		if( scanner.getColumn() > 0 ) {
			scanner.unread();
			int cc = scanner.read();
			if (!Character.isWhitespace((char) cc) && (char) cc != '('
					&& (char) cc != '{' && (char) cc != '/' && (char) cc != '!'
					&& (char) cc != '%' && (char) cc != '^' && (char) cc != '&'
					&& (char) cc != '*' && (char) cc != '-' && (char) cc != '+'
					&& (char) cc != '=' && (char) cc != '[' && (char) cc != '|'
					&& (char) cc != '?' && (char) cc != ':' && (char) cc != '<'
					&& (char) cc != '>' && (char) cc != ',') {
				return Token.UNDEFINED;
			}
		}
		if( this.noSameBefore ) {
			if( scanner.getColumn() != 0 ) {
				scanner.unread();
				int c = scanner.read();
				if( (char)c == start[0] ) {
					return Token.UNDEFINED;
				}
			}
		}
		char c1 = (char)scanner.read();
		if( c1 == start[0] ) {
			int index = 1;
			char ch = (char) scanner.read();
			if( Character.isJavaIdentifierStart(ch) || isInAddition(ch) || ( start.length > index && start[index] == ch ) ) {
				for( ;; ) {
					index++;
					int c = scanner.read();
					if( !Character.isJavaIdentifierPart((char) c) && !isInAddition((char)c ) && !( start.length > index && start[index] == ch ) ) {							
						break;
					}
				}
				scanner.unread();
				return token;
			}
			else
				scanner.unread();
		}

		scanner.unread();
		return Token.UNDEFINED;
	}
}