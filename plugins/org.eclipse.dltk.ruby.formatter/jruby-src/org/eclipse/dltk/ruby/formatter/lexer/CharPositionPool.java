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

import java.util.ArrayList;
import java.util.List;

public class CharPositionPool {

	private final List unused = new ArrayList();

	public CharRecord create(int ch, int column, int offset) {
		if (unused.isEmpty()) {
			return new CharRecord(ch, column, offset);
		} else {
			final CharRecord result = (CharRecord) unused
					.remove(unused.size() - 1);
			result.ch = ch;
			result.column = column;
			result.offset = offset;
			return result;
		}
	}

	public void release(CharRecord position) {
		position.ch = -1;
		position.column = -1;
		position.offset = -1;
		unused.add(position);
	}

}
