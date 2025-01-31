/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text;

import org.eclipse.dltk.ui.text.DLTKColorConstants;

public interface IRubyColorConstants {
	/**
	 * The color key for string and character literals in Ruby code.
	 */
	public static final String RUBY_STRING = DLTKColorConstants.DLTK_STRING;

	/**
	 * The color key for Ruby comments.
	 */
	public static final String RUBY_SINGLE_LINE_COMMENT = DLTKColorConstants.DLTK_SINGLE_LINE_COMMENT;

	/**
	 * The color key for Ruby numbers.
	 */
	public static final String RUBY_NUMBER = DLTKColorConstants.DLTK_NUMBER;

	/**
	 * The color key for Ruby keywords.
	 */
	public static final String RUBY_KEYWORD = DLTKColorConstants.DLTK_KEYWORD;

	/**
	 * The color key for Ruby keyword 'return'.
	 */
	public static final String RUBY_KEYWORD_RETURN = DLTKColorConstants.DLTK_KEYWORD_RETURN;

	/**
	 * The color key for Ruby code.
	 */
	public static final String RUBY_DEFAULT = DLTKColorConstants.DLTK_DEFAULT;

	/**
	 * The color key for Ruby code.
	 */
	public static final String RUBY_DOC = DLTKColorConstants.DLTK_DOC;

	/**
	 * The color key for Ruby code.
	 */
	public static final String RUBY_DOC_TOPIC = DLTKColorConstants.DLTK_DOC
			+ ".topic"; //$NON-NLS-1$

	/*
	 * The color key for TO-DO tasks in comments
	 */
	public static final String RUBY_TODO_COMMENT = DLTKColorConstants.TASK_TAG;

	public static final String RUBY_VARIABLE = "variable"; //$NON-NLS-1$
	public static final String RUBY_CLASS_VARIABLE = RUBY_VARIABLE + ".class"; //$NON-NLS-1$
	public static final String RUBY_GLOBAL_VARIABLE = RUBY_VARIABLE + ".global"; //$NON-NLS-1$
	public static final String RUBY_CONSTANT_VARIABLE = RUBY_VARIABLE
			+ ".constant"; //$NON-NLS-1$
	public static final String RUBY_INSTANCE_VARIABLE = RUBY_VARIABLE
			+ ".instance"; //$NON-NLS-1$
	public static final String RUBY_PSEUDO_VARIABLE = RUBY_VARIABLE + ".pseudo"; //$NON-NLS-1$
	public static final String RUBY_PREDEFINED_VARIABLE = RUBY_VARIABLE
			+ ".predefined"; //$NON-NLS-1$

	public static final String RUBY_SYMBOLS = "ruby.symbols"; //$NON-NLS-1$

	public static final String RUBY_REGEXP = RUBY_STRING + ".regexp"; //$NON-NLS-1$

	public static final String RUBY_EVAL_EXPR = RUBY_STRING + ".eval"; //$NON-NLS-1$

	public static final String RUBY_CONST = "const"; //$NON-NLS-1$
}
