/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.parser.mixin;

public class PrefixNoCaseMixinSearchPattern implements IMixinSearchPattern {

	private final String key;
	private final char[] chars;

	public PrefixNoCaseMixinSearchPattern(String key) {
		this.key = key;
		chars = key.toLowerCase().toCharArray();
	}

	@Override
	public boolean evaluate(String lastSegment) {
		final int length = chars.length;
		if (lastSegment.length() < length) {
			return false;
		}
		for (int i = 0; i < length; ++i) {
			if (Character.toLowerCase(lastSegment.charAt(i)) != chars[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getKey() {
		return key;
	}

}
