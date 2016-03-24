package org.eclipse.dltk.ruby.internal.debug.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.debug.ui.breakpoints.IScriptBreakpointLineValidator;
import org.eclipse.dltk.debug.ui.breakpoints.ScriptBreakpointLineValidatorFactory;
import org.eclipse.dltk.debug.ui.breakpoints.ScriptToggleBreakpointAdapter;
import org.eclipse.dltk.ruby.debug.RubyDebugConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class RubyToggleBreakpointAdapter extends ScriptToggleBreakpointAdapter {
	private static final IScriptBreakpointLineValidator validator = ScriptBreakpointLineValidatorFactory
			.createNonEmptyNoCommentValidator("#"); //$NON-NLS-1$

	@Override
	protected String getDebugModelId() {
		return RubyDebugConstants.DEBUG_MODEL_ID;
	}

	@Override
	protected IScriptBreakpointLineValidator getValidator() {
		return validator;
	}

	@Override
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {
		// Not implemented for ruby yet
	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		// Not implemented for ruby yet
	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleBreakpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {
		toggleLineBreakpoints(part, selection);
	}

	@Override
	public boolean canToggleBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return canToggleLineBreakpoints(part, selection);
	}
}
