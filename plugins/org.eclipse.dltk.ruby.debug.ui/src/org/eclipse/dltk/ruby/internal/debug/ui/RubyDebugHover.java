package org.eclipse.dltk.ruby.internal.debug.ui;

import org.eclipse.dltk.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.dltk.internal.debug.ui.ScriptDebugHover;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyDebugHover extends ScriptDebugHover {

	@Override
	protected ScriptDebugModelPresentation getModelPresentation() {
		return new RubyDebugModelPresentation();
	}

	@Override
	public void setPreferenceStore(IPreferenceStore store) {

	}
}
