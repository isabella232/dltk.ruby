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

import org.eclipse.dltk.formatter.FormatterTextNode;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterWriter;

public class FormatterStringNode extends FormatterTextNode {

	/**
	 * @param document
	 * @param startOffset
	 * @param endOffset
	 */
	public FormatterStringNode(IFormatterDocument document, int startOffset,
			int endOffset) {
		super(document, startOffset, endOffset);
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		visitor.ensureLineStarted(context);
		IFormatterContext strContext = context.copy();
		strContext.setIndenting(false);
		visitor.write(strContext, getStartOffset(), getEndOffset());
	}

}
