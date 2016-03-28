/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parser.mixin;

public class PrefixMixinSearchPattern implements IMixinSearchPattern {

	private final String prefix;

	/**
	 * @param prefix
	 */
	public PrefixMixinSearchPattern(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public final boolean evaluate(String lastSegment) {
		return lastSegment.startsWith(prefix);
	}

	@Override
	public String getKey() {
		return prefix;
	}

}
