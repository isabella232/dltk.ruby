/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.formatter.FormatterNodeRewriter;
import org.eclipse.dltk.formatter.FormatterTextNode;
import org.eclipse.dltk.formatter.FormatterUtils;
import org.eclipse.dltk.formatter.IFormatterCommentableNode;
import org.eclipse.dltk.formatter.IFormatterContainerNode;
import org.eclipse.dltk.formatter.IFormatterDocument;
import org.eclipse.dltk.formatter.IFormatterNode;
import org.eclipse.dltk.ruby.formatter.internal.nodes.FormatterCommentNode;
import org.jruby.ast.CommentNode;
import org.jruby.parser.RubyParserResult;

public class RubyFormatterNodeRewriter extends FormatterNodeRewriter {

	public RubyFormatterNodeRewriter(RubyParserResult result) {
		for (Iterator<CommentNode> i = result.getCommentNodes().iterator(); i.hasNext();) {
			CommentNode commentNode = i.next();
			if (!commentNode.isBlock()) {
				addComment(commentNode.getStartOffset(), commentNode
						.getEndOffset(), commentNode);
			}
		}
	}

	public void rewrite(IFormatterContainerNode root) {
		mergeTextNodes(root);
		insertComments(root);
		attachComments(root);
	}

	private void attachComments(IFormatterContainerNode root) {
		final List<FormatterTextNode> commentNodes = new ArrayList<FormatterTextNode>();
		final List<FormatterTextNode> comments = new ArrayList<FormatterTextNode>();
		final List<IFormatterNode> body = root.getBody();
		for (Iterator<IFormatterNode> i = body.iterator(); i.hasNext();) {
			IFormatterNode node = i.next();
			if (node instanceof FormatterCommentNode) {
				comments.add((FormatterCommentNode) node);
			} else if (FormatterUtils.isNewLine(node)
					&& !comments.isEmpty()
					&& comments.get(comments.size() - 1) instanceof FormatterCommentNode) {
				comments.add((FormatterTextNode) node);
			} else if (!comments.isEmpty()) {
				if (node instanceof IFormatterCommentableNode) {
					((IFormatterCommentableNode) node).insertBefore(comments);
					commentNodes.addAll(comments);
				}
				comments.clear();
			}
		}
		body.removeAll(commentNodes);
		for (Iterator<IFormatterNode> i = body.iterator(); i.hasNext();) {
			final IFormatterNode node = i.next();
			if (node instanceof IFormatterContainerNode) {
				attachComments((IFormatterContainerNode) node);
			}
		}
	}

	@Override
	protected IFormatterNode createCommentNode(IFormatterDocument document,
			int startOffset, int endOffset, Object object) {
		return new FormatterCommentNode(document, startOffset, endOffset);
	}

}
