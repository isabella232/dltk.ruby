/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text.rules;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.dltk.ruby.core.utils.RubySyntaxUtils;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.PatternRule;

public class RubySlashRegexpRule extends PatternRule {

	public RubySlashRegexpRule(IToken token) {
		super("/", "/", token, '\\', true, false); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		IToken token = super.evaluate(scanner, resume);
		if (token.isUndefined())
			return token;
		processRegexpOptions(scanner);
		return token;
	}

	/**
	 * Comparator that orders <code>char[]</code> in decreasing array lengths.
	 * 
	 * @since 3.1
	 */
	private static class DecreasingCharArrayLengthComparator implements
			Comparator<char[]> {
		@Override
		public int compare(char[] o1, char[] o2) {
			return o2.length - o1.length;
		}
	}

	/**
	 * Line delimiter comparator which orders according to decreasing delimiter
	 * length.
	 * 
	 * @since 3.1
	 */
	private Comparator<char[]> fLineDelimiterComparator = new DecreasingCharArrayLengthComparator();

	/**
	 * Cached line delimiters.
	 * 
	 * @since 3.1
	 */
	private char[][] fLineDelimiters;

	/**
	 * Cached sorted {@linkplain #fLineDelimiters}.
	 * 
	 * @since 3.1
	 */
	private char[][] fSortedLineDelimiters;

	/**
	 * Returns whether the end sequence was detected. As the pattern can be
	 * considered ended by a line delimiter, the result of this method is
	 * <code>true</code> if the rule breaks on the end of the line, or if the
	 * EOF character is read.
	 * 
	 * @param scanner
	 *            the character scanner to be used
	 * @return <code>true</code> if the end sequence has been detected
	 */
	@Override
	protected boolean endSequenceDetected(ICharacterScanner scanner) {
		/*
		 * This method is copied from PatternRule.
		 * 
		 * The only change is that fBreaksOnEOL toggles negative match.
		 */
		char[][] originalDelimiters = scanner.getLegalLineDelimiters();
		int count = originalDelimiters.length;
		if (fLineDelimiters == null || originalDelimiters.length != count) {
			fSortedLineDelimiters = new char[count][];
		} else {
			while (count > 0
					&& fLineDelimiters[count - 1] == originalDelimiters[count - 1])
				count--;
		}
		if (count != 0) {
			fLineDelimiters = originalDelimiters;
			System.arraycopy(fLineDelimiters, 0, fSortedLineDelimiters, 0,
					fLineDelimiters.length);
			Arrays.sort(fSortedLineDelimiters, fLineDelimiterComparator);
		}

		int readCount = 1;
		int c;
		OUTER_LOOP: while ((c = scanner.read()) != ICharacterScanner.EOF) {
			if (c == fEscapeCharacter) {
				// Skip escaped character(s)
				if (fEscapeContinuesLine) {
					c = scanner.read();
					for (int i = 0; i < fSortedLineDelimiters.length; i++) {
						if (c == fSortedLineDelimiters[i][0]
								&& sequenceDetected(scanner,
										fSortedLineDelimiters[i], true))
							break;
					}
				} else
					scanner.read();

			} else if (fEndSequence.length > 0 && c == fEndSequence[0]) {
				// Check if the specified end sequence has been found.
				if (sequenceDetected(scanner, fEndSequence, true))
					return true;
			} else if (fBreaksOnEOL) {
				// Check for end of line since it can be used to <b>break</b>
				// the pattern.
				for (int i = 0; i < fSortedLineDelimiters.length; i++) {
					if (c == fSortedLineDelimiters[i][0]
							&& sequenceDetected(scanner,
									fSortedLineDelimiters[i], true))
						break OUTER_LOOP;
				}
			}
			readCount++;
		}

		for (; readCount > 0; readCount--)
			scanner.unread();

		return false;
	}

	private void processRegexpOptions(ICharacterScanner scanner) {
		int c;
		while ((c = scanner.read()) != ICharacterScanner.EOF) {
			if (!RubySyntaxUtils.isValidRegexpModifier((char) c))
				break;
		}
		scanner.unread();
	}

}
