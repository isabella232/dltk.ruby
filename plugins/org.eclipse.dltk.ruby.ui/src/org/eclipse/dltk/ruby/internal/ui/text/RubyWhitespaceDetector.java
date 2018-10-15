/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class RubyWhitespaceDetector implements IWhitespaceDetector {

	@Override
	public boolean isWhitespace(char character) {
		return Character.isWhitespace(character);
	}
}
