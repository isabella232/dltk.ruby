/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.core.tests.typeinference;

import junit.framework.Test;

public class SimpleTest {

	public static Test suite() {
		return new TypeInferenceSuite(SimpleTest.class,
				"/workspace/typeinference/simple");
	}

}
