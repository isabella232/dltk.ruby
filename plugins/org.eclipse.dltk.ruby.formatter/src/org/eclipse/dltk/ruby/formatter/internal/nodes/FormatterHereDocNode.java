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
import org.eclipse.dltk.formatter.FormatterUtils;
import org.eclipse.dltk.formatter.IFormatterCallback;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterRawWriter;
import org.eclipse.dltk.formatter.IFormatterWriter;
import org.eclipse.jface.text.IRegion;

public class FormatterHereDocNode extends FormatterTextNode implements
		IFormatterCallback {

	private final boolean indent;
	private IRegion contentRegion;
	private IRegion endMarkerRegion;

	/**
	 * @param document
	 * @param startOffset
	 * @param endOffset
	 */
	public FormatterHereDocNode(IFormatterDocument document, int startOffset,
			int endOffset, boolean indent) {
		super(document, startOffset, endOffset);
		this.indent = indent;
	}

	/**
	 * @return the contentRegion
	 */
	public IRegion getContentRegion() {
		return contentRegion;
	}

	/**
	 * @param contentRegion
	 *            the contentRegion to set
	 */
	public void setContentRegion(IRegion contentRegion) {
		this.contentRegion = contentRegion;
	}

	/**
	 * @return the endMarkerRegion
	 */
	public IRegion getEndMarkerRegion() {
		return endMarkerRegion;
	}

	/**
	 * @param endMarkerRegion
	 *            the endMarkerRegion to set
	 */
	public void setEndMarkerRegion(IRegion endMarkerRegion) {
		this.endMarkerRegion = endMarkerRegion;
	}

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		IFormatterContext heredocContext = context.copy();
		heredocContext.setIndenting(false);
		visitor.write(heredocContext, getStartOffset(), getEndOffset());
		if (contentRegion != null) {
			visitor.excludeRegion(contentRegion);
		}
		if (endMarkerRegion != null) {
			visitor.excludeRegion(endMarkerRegion);
		}
		visitor.addNewLineCallback(this);
	}

	@Override
	public void call(IFormatterContext context, IFormatterRawWriter writer) {
		final IFormatterDocument doc = getDocument();
		if (contentRegion != null && contentRegion.getLength() > 0) {
			writer.writeText(context, doc.get(contentRegion));
		}
		if (endMarkerRegion != null) {
			final String endMarker = doc.get(endMarkerRegion);
			if (indent) {
				writer.writeIndent(context);
				int i = 0;
				while (i < endMarker.length()
						&& FormatterUtils.isSpace(endMarker.charAt(i))) {
					++i;
				}
				writer.writeText(context, endMarker.substring(i));
			} else {
				writer.writeText(context, endMarker);
			}
		}
	}

	/**
	 * @return the indent
	 */
	public boolean isIndent() {
		return indent;
	}

}
