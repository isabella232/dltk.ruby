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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.formatter.FormatterBlockNode;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterNode;
import org.eclipse.dltk.formatter.IFormatterTextNode;
import org.eclipse.dltk.formatter.IFormatterWriter;

public abstract class FormatterBlockWithBeginNode extends FormatterBlockNode {

	/**
	 * @param document
	 */
	public FormatterBlockWithBeginNode(IFormatterDocument document) {
		super(document);
	}

	private IFormatterTextNode begin;

	@Override
	public void accept(IFormatterContext context, IFormatterWriter visitor)
			throws Exception {
		if (begin != null) {
			visitor
					.write(context, begin.getStartOffset(), begin
							.getEndOffset());
		}
		final boolean indenting = isIndenting();
		if (indenting) {
			context.incIndent();
		}
		super.accept(context, visitor);
		if (indenting) {
			context.decIndent();
		}
	}

	/**
	 * @return the begin
	 */
	public IFormatterTextNode getBegin() {
		return begin;
	}

	/**
	 * @param begin
	 *            the begin to set
	 */
	public void setBegin(IFormatterTextNode begin) {
		this.begin = begin;
	}

	@Override
	public int getStartOffset() {
		if (begin != null) {
			return begin.getStartOffset();
		}
		return super.getStartOffset();
	}

	@Override
	public int getEndOffset() {
		if (!super.isEmpty()) {
			return super.getEndOffset();
		}
		if (begin != null) {
			return begin.getEndOffset();
		}
		return DEFAULT_OFFSET;
	}

	@Override
	public boolean isEmpty() {
		return begin == null && super.isEmpty();
	}

	@Override
	public List<IFormatterNode> getChildren() {
		if (begin == null) {
			return super.getChildren();
		} else {
			List<IFormatterNode> result = new ArrayList<IFormatterNode>();
			if (begin != null) {
				result.add(begin);
			}
			result.addAll(super.getChildren());
			return result;
		}
	}

	@Override
	public String toString() {
		return begin + "\n" + super.toString(); //$NON-NLS-1$
	}

	protected boolean isIndenting() {
		return true;
	}

}
