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
package org.eclipse.dltk.ruby.formatter.internal;

import org.eclipse.dltk.formatter.FormatterContext;
import org.eclipse.dltk.formatter.IFormatterContainerNode;
import org.eclipse.dltk.formatter.IFormatterNode;
import org.eclipse.dltk.ruby.formatter.internal.nodes.FormatterRequireNode;

public class RubyFormatterContext extends FormatterContext {

	public RubyFormatterContext(int indent) {
		super(indent);
	}

	@Override
	protected boolean isCountable(IFormatterNode node) {
		return node instanceof IFormatterContainerNode
				|| node instanceof FormatterRequireNode;
	}

}
