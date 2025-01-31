package org.eclipse.dltk.ruby.fastdebugger;

import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterRunner;
import org.eclipse.dltk.launching.IInterpreterRunnerFactory;

public class FastDebuggerRunnerFactory implements
		IInterpreterRunnerFactory {
	@Override
	public IInterpreterRunner createRunner(IInterpreterInstall install) {
		return new FastDebuggerRunner(install);
	}
}
