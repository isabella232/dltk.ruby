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
package org.eclipse.dltk.ruby.formatter.internal.nodes;

import org.eclipse.dltk.formatter.IFormatterCommentableNode;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;

public class FormatterMethodNode extends FormatterBlockWithBeginEndNode
		implements IFormatterCommentableNode {

	/**
	 * @param document
	 */
	public FormatterMethodNode(IFormatterDocument document) {
		super(document);
	}

	@Override
	protected boolean isIndenting() {
		return getDocument().getBoolean(RubyFormatterConstants.INDENT_METHOD);
	}

	@Override
	protected int getBlankLinesBefore(IFormatterContext context) {
		if (context.getParent() == null) {
			return getInt(RubyFormatterConstants.LINES_FILE_BETWEEN_METHOD);
		} else if (context.getChildIndex() == 0) {
			return getInt(RubyFormatterConstants.LINES_BEFORE_FIRST);
		} else {
			return getInt(RubyFormatterConstants.LINES_BEFORE_METHOD);
		}
	}

	@Override
	protected int getBlankLinesAfter(IFormatterContext context) {
		if (context.getParent() == null) {
			return getInt(RubyFormatterConstants.LINES_FILE_BETWEEN_METHOD);
		} else {
			return super.getBlankLinesAfter(context);
		}
	}

}
