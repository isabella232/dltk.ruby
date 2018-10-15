/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
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
package org.eclipse.dltk.ruby.debug;

import java.util.regex.Pattern;

import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;

/**
 * Implementation of the {@link IDynamicVariableResolver} to return the regular
 * expression to match filename and line number in ruby console.
 */
public class RubyFilenameLinenumberResolver implements IDynamicVariableResolver {

	private static final String DEVICE = "(?:[a-zA-Z]:)?"; //$NON-NLS-1$
	private static final String SEPARATOR = "[/\\\\]"; //$NON-NLS-1$
	private static final String SEGMENT = "[ _\\p{L}\\p{N}\\p{M}\\p{S}\\.\\-]+"; //$NON-NLS-1$
	private static final String LINE_NUMBER = "(\\d+)"; //$NON-NLS-1$

	@Override
	public String resolveValue(IDynamicVariable variable, String argument) {
		final StringBuffer sb = new StringBuffer(128);
		//		sb.append("(?<!(?:\\W|^))"); //$NON-NLS-1$ not word char before
		sb.append('('); // BEGIN file name group
		sb.append(DEVICE);
		sb.append(SEPARATOR).append('?');
		sb.append(SEGMENT);
		sb.append("(?:").append(SEPARATOR).append(SEGMENT).append(")*"); //$NON-NLS-1$//$NON-NLS-2$
		sb.append(')'); // END file name group
		sb.append(':');
		sb.append(LINE_NUMBER);
		//		sb.append("(?=(?:\\W|$))"); //$NON-NLS-1$ not word char after
		return sb.toString();
	}

	public static Pattern createPattern() {
		return Pattern.compile(getRegex());
	}

	public static String getRegex() {
		return new RubyFilenameLinenumberResolver().resolveValue(null, null);
	}
}
