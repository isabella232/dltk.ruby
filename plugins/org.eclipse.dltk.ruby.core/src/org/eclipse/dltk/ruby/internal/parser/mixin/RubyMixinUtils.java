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
package org.eclipse.dltk.ruby.internal.parser.mixin;

public class RubyMixinUtils {

	public static boolean isObject(String key) {
		return OBJECT.equals(key) || OBJECT_INSTANCE.equals(key);
	}

	public static boolean isKernel(String key) {
		return KERNEL.equals(key) || KERNEL_INSTANCE.equals(key);
	}

	public static boolean isObjectOrKernel(String key) {
		return isObject(key) || isKernel(key);
	}

	public static final String OBJECT = "Object"; //$NON-NLS-1$

	public static final String OBJECT_INSTANCE = OBJECT
			+ RubyMixin.INSTANCE_SUFFIX;

	public static final String KERNEL = "Kernel"; //$NON-NLS-1$

	public static final String KERNEL_INSTANCE = KERNEL
			+ RubyMixin.INSTANCE_SUFFIX;

}
