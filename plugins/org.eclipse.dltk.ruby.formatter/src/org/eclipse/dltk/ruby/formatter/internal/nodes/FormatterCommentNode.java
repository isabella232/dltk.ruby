/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.internal.nodes;

import org.eclipse.dltk.formatter.FormatterTextNode;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterWriter;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;

public class FormatterCommentNode extends FormatterTextNode {

	public FormatterCommentNode(IFormatterDocument document, int startOffset,
			int endOffset) {
		super(document, startOffset, endOffset);
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		final boolean savedWrapping = context.isWrapping();
		final boolean savedComment = context.isComment();
		final boolean isWrapping = getDocument().getBoolean(
				RubyFormatterConstants.WRAP_COMMENTS);
		if (isWrapping) {
			context.setWrapping(true);
		}
		context.setComment(true);
		visitor.write(context, getStartOffset(), getEndOffset());
		if (isWrapping) {
			context.setWrapping(savedWrapping);
		}
		context.setComment(savedComment);
	}

}
