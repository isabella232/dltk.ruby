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

public class StringLexerReader implements ILexerReader {

	private int offset = 0;
	private int line = 0;
	private int column = 0;
	private final String content;
	private boolean nextCharIsOnANewLine = false;

	/**
	 * @param content
	 */
	public StringLexerReader(String content) {
		this.content = content;
	}

	public int peek() {
		return offset < content.length() ? content.charAt(offset) : EOF;
	}

	public int read() {
		if (offset < content.length()) {
			if (nextCharIsOnANewLine) {
				column = 0;
				nextCharIsOnANewLine = false;
			}
			++column;
			return content.charAt(offset++);
		} else {
			return EOF;
		}
	}

	public int getColumn() {
		return column;
	}

	public int getOffset() {
		return offset;
	}

	public int getLine() {
		return line;
	}

	public void newLine() {
		++line;
		nextCharIsOnANewLine = true;
	}

}
