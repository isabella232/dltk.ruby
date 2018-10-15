/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.ui.tests.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ruby.internal.ui.text.IRubyPartitions;
import org.eclipse.dltk.ruby.internal.ui.text.RubyPartitionScanner;
import org.eclipse.dltk.ui.text.util.IRangeFilter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.FastPartitioner;

public class TestUtils {

	public static String getData(String filename) throws IOException {
		File file = RubyUITestsPlugin.getDefault().getFileInPlugin(new Path(filename));
		try (InputStream stream = new FileInputStream(file.getAbsolutePath())) {
			int length = stream.available();
			byte[] data = new byte[length];
			stream.read(data);
			return new String(data, StandardCharsets.UTF_8);
		}
	}

	/**
	 * Installs a partitioner with <code>document</code>.
	 *
	 * @param document
	 *            the document
	 */
	public static void installStuff(Document document) {
		String[] types = new String[] { IRubyPartitions.RUBY_STRING, IRubyPartitions.RUBY_PERCENT_STRING,
				IRubyPartitions.RUBY_COMMENT, IDocument.DEFAULT_CONTENT_TYPE };
		FastPartitioner partitioner = new FastPartitioner(new RubyPartitionScanner(), types);
		partitioner.connect(document);
		document.setDocumentPartitioner(IRubyPartitions.RUBY_PARTITIONING, partitioner);
	}

	public static final IRangeFilter ALL_RANGES_ALLOWED = (document, start, length) -> true;

}
