/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation
 *     xored software, Inc. - todo task parser added (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parser;

import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.core.AbstractSourceElementParser;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.ruby.internal.parser.visitors.RubySourceElementRequestor;

public class RubySourceElementParser extends AbstractSourceElementParser {

	@Override
	protected SourceElementRequestVisitor createVisitor() {
		return new RubySourceElementRequestor(getRequestor());
	}

	@Override
	protected String getNatureId() {
		return RubyNature.NATURE_ID;
	}

}
