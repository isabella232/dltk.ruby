/*******************************************************************************
 * Copyright (c) 2016 xored software, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.tests;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.formatter.tests.ScriptedTest.IScriptedTestContext;
import org.eclipse.dltk.ruby.formatter.RubyFormatter;
import org.eclipse.dltk.ui.formatter.IScriptFormatter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RubyFormatterTestsPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.ruby.formatter.tests"; //$NON-NLS-1$

	// The shared instance
	private static RubyFormatterTestsPlugin plugin;

	/**
	 * The constructor
	 */
	public RubyFormatterTestsPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RubyFormatterTestsPlugin getDefault() {
		return plugin;
	}

	public static void warn(String message) {
		warn(message, null);
	}

	public static void warn(String message, Throwable throwable) {
		log(new Status(IStatus.WARNING, PLUGIN_ID, 0, message, throwable));
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, throwable));
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static final IScriptedTestContext CONTEXT = new IScriptedTestContext() {

		@Override
		public Bundle getResourceBundle() {
			return getDefault().getBundle();
		}

		@Override
		public String getCharset() {
			return AllTests.CHARSET;
		}

		@Override
		public IScriptFormatter createFormatter(
				Map<String, Object> preferences) {
			if (preferences != null) {
				final Map<String, Object> prefs = TestRubyFormatter
						.createTestingPreferences();
				prefs.putAll(preferences);
				return new TestRubyFormatter(Util.LINE_SEPARATOR, prefs);
			} else {
				return new TestRubyFormatter();
			}
		}

		@Override
		public String validateOptionName(String name) {
			if (RubyFormatter.isBooleanOption(name)
					|| RubyFormatter.isIntegerOption(name)) {
				return name;
			} else {
				return null;
			}
		}

		@Override
		public String validateOptionValue(String name, String value) {
			if (RubyFormatter.isBooleanOption(name)) {
				return "false".equals(value) || "true".equals(value) ? value
						: null;
			} else if (RubyFormatter.isIntegerOption(name)) {
				try {
					Integer.parseInt(value);
					return value;
				} catch (NumberFormatException e) {
					return null;
				}
			} else {
				return null;
			}
		}
	};

}
