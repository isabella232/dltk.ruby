/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.parsers.jruby;

import org.jruby.common.NullWarnings;
import org.jruby.lexer.yacc.ISourcePosition;

public class DLTKRubyNullWarnings extends NullWarnings implements IDLTKRubyWarnings {

	@Override
	public void error(ISourcePosition position, String message) {}

	
}
