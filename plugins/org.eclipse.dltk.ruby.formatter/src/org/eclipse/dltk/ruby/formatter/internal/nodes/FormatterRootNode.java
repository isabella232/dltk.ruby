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

import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.formatter.FormatterBlockNode;
import org.eclipse.dltk.formatter.FormatterUtils;
import org.eclipse.dltk.formatter.IFormatterContainerNode;
import org.eclipse.dltk.formatter.IFormatterContext;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterNode;
import org.eclipse.dltk.formatter.IFormatterWriter;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;

public class FormatterRootNode extends FormatterBlockNode {

	/**
	 * @param document
	 */
	public FormatterRootNode(IFormatterDocument document) {
		super(document);
	}

	@Override
	protected void acceptNodes(final List<IFormatterNode> nodes, IFormatterContext context,
			IFormatterWriter visitor) throws Exception {
		boolean wasRequire = false;
		for (Iterator<IFormatterNode> i = nodes.iterator(); i.hasNext();) {
			IFormatterNode node = i.next();
			context.enter(node);
			if (node instanceof FormatterRequireNode) {
				if (wasRequire) {
					context.setBlankLines(0);
				}
			} else if (wasRequire
					&& (node instanceof IFormatterContainerNode || !FormatterUtils
							.isEmptyText(node))) {
				context
						.setBlankLines(getInt(RubyFormatterConstants.LINES_FILE_AFTER_REQUIRE));
				wasRequire = false;
			}
			node.accept(context, visitor);
			context.leave(node);
			if (node instanceof FormatterRequireNode) {
				wasRequire = true;
			}
		}
	}

}
