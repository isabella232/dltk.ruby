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
package org.eclipse.dltk.ruby.formatter.internal.nodes;

import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterWriter;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;

public class FormatterRescueNode extends FormatterBlockWithBeginNode {

	/**
	 * @param document
	 */
	public FormatterRescueNode(IFormatterDocument document) {
		super(document);
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		if (getBegin() != null) {
			final boolean indenting = isIndenting();
			if (indenting) {
				context.decIndent();
			}
			visitor.write(context, getBegin().getStartOffset(), getBegin()
					.getEndOffset());
			if (indenting) {
				context.incIndent();
			}
		}
		acceptBody(context, visitor);
	}

	@Override
	protected boolean isIndenting() {
		return getDocument().getBoolean(RubyFormatterConstants.INDENT_BLOCKS);
	}

}
