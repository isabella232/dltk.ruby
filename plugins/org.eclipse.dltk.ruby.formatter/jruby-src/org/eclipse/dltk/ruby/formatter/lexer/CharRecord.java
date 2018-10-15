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

public class CharRecord {

	int ch;
	int column;
	int offset;

	/**
	 * @param ch
	 * @param column
	 * @param line
	 * @param offset
	 */
	public CharRecord(int ch, int column, int offset) {
		this.ch = ch;
		this.column = column;
		this.offset = offset;
	}

	/**
	 * @return the ch
	 */
	public int getCh() {
		return ch;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	public String toString() {
		return "'" + (char) ch + "' offset=" + offset;
	}

}
