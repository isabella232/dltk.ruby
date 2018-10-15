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
package org.eclipse.dltk.ruby.internal.ui.docs;

/**
 * Interface for classes that want to be notified by Watchdog.
 * 
 * @see org.apache.commons.exec.Watchdog
 */
public interface WatchdogListener {

	/**
	 * Called when the {@link Watchdog} times out.
	 */
	void timeoutOccured();
}
