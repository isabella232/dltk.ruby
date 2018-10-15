/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.core.search;

import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class RubySearchFactory extends AbstractSearchFactory {

	@Override
	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new RubyMatchLocatorParser(locator);
	}

	@Override
	public ISearchPatternProcessor createSearchPatternProcessor() {
		return new RubySearchPatternProcessor();
	}
}
