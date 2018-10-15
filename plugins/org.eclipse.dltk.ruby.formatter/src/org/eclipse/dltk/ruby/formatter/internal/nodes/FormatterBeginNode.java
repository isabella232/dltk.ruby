/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
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

import org.eclipse.dltk.formatter.IFormatterDocument;

/**
 * <code>
 * begin
 * 	  do_something
 * rescue
 * 	  recover
 * ensure
 * 	  must_to_do
 * end
 * </code>
 */
public class FormatterBeginNode extends FormatterBlockWithBeginEndNode {

	/**
	 * @param document
	 */
	public FormatterBeginNode(IFormatterDocument document) {
		super(document);
	}

}
