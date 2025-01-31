/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc.
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
package org.eclipse.dltk.ruby.internal.ui.text;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ui.editor.highlighting.SemanticHighlighting;

public class RubySemanticHighlighting extends SemanticHighlighting {

	private final String preferenceKey;
	private final String displayName;
	private final boolean enablement;

	public RubySemanticHighlighting(String preferenceKey, String displayName) {
		this(preferenceKey, displayName, true);
	}

	public RubySemanticHighlighting(String preferenceKey, String displayName,
			boolean enablement) {
		Assert.isNotNull(preferenceKey);
		this.preferenceKey = preferenceKey;
		this.displayName = displayName;
		this.enablement = enablement;
	}

	@Override
	public String getPreferenceKey() {
		return preferenceKey;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean isSemanticOnly() {
		return displayName != null;
	}

	@Override
	public String getEnabledPreferenceKey() {
		return enablement ? super.getEnabledPreferenceKey() : null;
	}

	@Override
	public int hashCode() {
		return preferenceKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof RubySemanticHighlighting) {
			final RubySemanticHighlighting other = (RubySemanticHighlighting) obj;
			return preferenceKey.equals(other.preferenceKey);
		}
		return false;
	}

}
