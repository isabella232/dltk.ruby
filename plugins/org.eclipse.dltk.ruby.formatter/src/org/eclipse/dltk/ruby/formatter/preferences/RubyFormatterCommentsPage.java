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
package org.eclipse.dltk.ruby.formatter.preferences;

import java.net.URL;

import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;
import org.eclipse.dltk.ui.formatter.FormatterModifyTabPage;
import org.eclipse.dltk.ui.formatter.IFormatterControlManager;
import org.eclipse.dltk.ui.formatter.IFormatterModifyDialog;
import org.eclipse.dltk.ui.util.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class RubyFormatterCommentsPage extends FormatterModifyTabPage {

	/**
	 * @param dialog
	 */
	public RubyFormatterCommentsPage(IFormatterModifyDialog dialog) {
		super(dialog);
	}

	@Override
	protected void createOptions(IFormatterControlManager manager,
			Composite parent) {
		Group commentWrappingGroup = SWTFactory.createGroup(parent,
				Messages.RubyFormatterCommentsPage_commentFormatting, 2, 1, GridData.FILL_HORIZONTAL);
		manager.createCheckbox(commentWrappingGroup,
				RubyFormatterConstants.WRAP_COMMENTS,
				Messages.RubyFormatterCommentsPage_enableCommentWrapping, 2);
		manager.createNumber(commentWrappingGroup,
				RubyFormatterConstants.WRAP_COMMENTS_LENGTH,
				Messages.RubyFormatterCommentsPage_maximumLineWidthForComments);
	}

	@Override
	protected URL getPreviewContent() {
		return getClass().getResource("wrapping-preview.rb"); //$NON-NLS-1$
	}

}
