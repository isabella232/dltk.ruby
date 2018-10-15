/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class PredefinedVariables {
	private static final String MESSAGE_PROPERTIES = "variables.properties"; //$NON-NLS-1$

	private static final String NAME = "name"; //$NON-NLS-1$

	private static final String TYPE = "type"; //$NON-NLS-1$

	private static final String DOC = "doc"; //$NON-NLS-1$

	static Map<String, Map<String, String>> parseProperties(Properties props, String[] postfixes) {
		Map<String, Map<String, String>> entries = new HashMap<String, Map<String, String>>();

		Iterator it = props.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();

			for (int i = 0; i < postfixes.length; ++i) {
				final String postfix = postfixes[i];

				int index = key.indexOf("_" + postfix); //$NON-NLS-1$
				if (index != -1) {
					String name = key.substring(0, index);

					Map<String, String> entry = entries.get(name);

					if (entry == null) {
						entry = new HashMap<String, String>();
						entries.put(name, entry);
					}

					entry.put(postfix, (String) props.get(key));
				}
			}
		}

		return entries;
	}

	private static Map<String, String> nameToTypeMap = new HashMap<String, String>();
	private static Map<String, String> nameToDocMap = new HashMap<String, String>();

	public static String getTypeOf(String name) {
		return nameToDocMap.get(name);
	}

	public static String getDocOf(String name) {
		return nameToDocMap.get(name);
	}

	static {
		try {
			URL url = RubyPlugin.getDefault().getBundle().getEntry(
					MESSAGE_PROPERTIES);
			InputStream input = null;
			try {
				input = new BufferedInputStream(url.openStream());
				Properties props = new Properties();
				props.load(input);

				Map<String, Map<String, String>> parsedProps = parseProperties(props, new String[] { NAME,
						TYPE, DOC });

				Iterator<String> it = parsedProps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					Map<String, String> entry = parsedProps.get(key);

					String name = entry.get(NAME);
					String type = entry.get(TYPE);
					String doc = entry.get(DOC);

					nameToTypeMap.put(name, type);
					nameToDocMap.put(name, doc);

					// System.out.println("Name: " + name + "; Type: " + type + "; Doc: " + doc);
				}
			} finally {
				if (input != null) {
					input.close();
				}
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, RubyPlugin.PLUGIN_ID, 0,
					Messages.PredefinedVariables_unableToLoadRubyPredefinedVariables, e);
			RubyPlugin.getDefault().getLog().log(status);
		}
	}
}
