/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core.search;

import org.eclipse.dltk.core.search.SearchPatternProcessor;

public class RubySearchPatternProcessor extends SearchPatternProcessor {

	private static final String TYPE_DELIMITER = "::"; //$NON-NLS-1$

	private static final String METHOD_DELIMITER = "::"; //$NON-NLS-1$

	@Override
	public char[] extractDeclaringTypeQualification(String pattern) {
		final int pos = pattern.lastIndexOf(METHOD_DELIMITER);
		if (pos != -1) {
			final String type = pattern.substring(0, pos);
			return extractTypeQualification(type);
		}
		return null;
	}

	@Override
	public char[] extractDeclaringTypeSimpleName(String pattern) {
		final int pos = pattern.lastIndexOf(METHOD_DELIMITER);
		if (pos != -1) {
			final String type = pattern.substring(0, pos);
			return extractTypeChars(type).toCharArray();
		}
		return null;
	}

	@Override
	public char[] extractSelector(String pattern) {
		final int pos = pattern.lastIndexOf(METHOD_DELIMITER);
		if (pos != -1) {
			final int begin = pos + METHOD_DELIMITER.length();
			if (begin < pattern.length()) {
				final char[] result = new char[pattern.length() - begin];
				pattern.getChars(begin, pattern.length(), result, 0);
				return result;
			}
		}
		return pattern.toCharArray();
	}

	@Override
	public ITypePattern parseType(String patternString) {
		final int pos = patternString.lastIndexOf(TYPE_DELIMITER);
		if (pos != -1) {
			return new TypePattern(patternString.substring(0, pos).replace(
					TYPE_DELIMITER, TYPE_SEPARATOR_STR),
					patternString.substring(pos + TYPE_DELIMITER.length()));
		} else {
			return new TypePattern(null, patternString);
		}
	}

	@Override
	public String getDelimiterReplacementString() {
		return TYPE_DELIMITER;
	}

}
