/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core.codeassist;

import java.util.ArrayList;
import java.util.List;

public class RubyKeyword {
	private static String[] sKeywords = new String[] {"if", "else", "elsif", "unless", "while", "until", "in" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		,"case", "when", "begin", "ensure", "module", "for", "then", "do", "and", "or", "not" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
		,"rescue", "return", "break", "next", "yield", "defined?", "super", "def", "undef", "alias", "class" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
		,"end", "self", "false", "true", "retry", "nil", "redo", "BEGIN", "END", "__LINE__", "__FILE__", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$
		"sub", "sub!", "gsub", "gsub!", "scan", "index", "match", "require" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	
	public static String[] findByPrefix (String prefix) {
		List<String> result = new ArrayList<String> ();
		for (int i = 0; i < sKeywords.length; i++) {
			if (sKeywords[i].startsWith(prefix))
				result.add(sKeywords[i]);
		}		
		return result.toArray(new String[result.size()]);
	}
}
