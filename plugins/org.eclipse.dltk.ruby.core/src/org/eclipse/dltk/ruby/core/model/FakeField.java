/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.model;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;

public class FakeField extends SourceField implements ISourceRange {
	private final int offset;
	private final int length;
	private final int flags;
	private final boolean hasFlags;

	public FakeField(ISourceModule parent, String name, int offset, int length) {
		super((ModelElement) parent, name);
		this.offset = offset;
		this.length = length;
		this.flags = 0;
		this.hasFlags = false;
	}

	public FakeField(ISourceModule parent, String name, int offset, int length,
			int flags) {
		super((ModelElement) parent, name);
		this.offset = offset;
		this.length = length;
		this.flags = flags;
		this.hasFlags = true;
	}

	@Override
	public ISourceRange getNameRange() throws ModelException {
		return this;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		return this;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public int getFlags() throws ModelException {
		return hasFlags ? flags : super.getFlags();
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getOffset() {
		return offset;
	}
}
