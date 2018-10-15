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
package org.eclipse.dltk.ruby.testing.internal.rspec;

public class RSpecUtils {

	public static final String DESCRIBE = "describe"; //$NON-NLS-1$

	public static final String CONTEXT = "context"; //$NON-NLS-1$

	public static final String[] CONTEXT_METHODS = { DESCRIBE, CONTEXT };

	public static final String[] TEST_METHODS = { "it", //$NON-NLS-1$
			"specify" //$NON-NLS-1$
	};

	public static final String[] TEST_SHARED = { "it_should_behave_like" //$NON-NLS-1$
	};

	public static final String[] SHARED_GROUP = { "shared_examples_for", //$NON-NLS-1$
			"share_examples_for", //$NON-NLS-1$
			"share_as" //$NON-NLS-1$
	};

}
