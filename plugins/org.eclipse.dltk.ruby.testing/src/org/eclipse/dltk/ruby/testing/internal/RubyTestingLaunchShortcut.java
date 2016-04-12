/*******************************************************************************
 * Copyright (c) 2008, 2016 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.testing.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.testing.launcher.DLTKTestingMigrationDelegate;
import org.eclipse.dltk.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.eclipse.dltk.testing.DLTKTestingConstants;
import org.eclipse.dltk.testing.ITestingEngine;
import org.eclipse.dltk.testing.TestingEngineDetectResult;
import org.eclipse.dltk.testing.TestingEngineManager;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class RubyTestingLaunchShortcut implements ILaunchShortcut {

	/**
	 * Default constructor.
	 */
	public RubyTestingLaunchShortcut() {
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IModelElement element = DLTKUIPlugin.getEditorInputModelElement(editor
				.getEditorInput());
		if (element != null) {
			launch(new Object[] { element }, mode);
		} else {
			showNoTestsFoundDialog();
		}
	}

	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			launch(((IStructuredSelection) selection).toArray(), mode);
		} else {
			showNoTestsFoundDialog();
		}
	}

	private void launch(Object[] elements, String mode) {
		try {
			IModelElement elementToLaunch = null;

			if (elements.length == 1) {
				Object selected = elements[0];
				if (selected instanceof IFolder) {
					performLaunch((IFolder) selected, mode);
					return;
				}
				if (!(selected instanceof IModelElement)
						&& selected instanceof IAdaptable) {
					selected = ((IAdaptable) selected)
							.getAdapter(IModelElement.class);
				}
				if (selected instanceof IModelElement) {
					IModelElement element = (IModelElement) selected;
					switch (element.getElementType()) {
					case IModelElement.SCRIPT_PROJECT: {
						IProject project = ((IScriptProject) element)
								.getProject();
						List<ILaunchConfiguration> configs = new ArrayList<ILaunchConfiguration>();
						IFolder specFolder = project.getFolder("test"); //$NON-NLS-1$
						if (specFolder != null && specFolder.exists())
							configs.add(findOrCreateLaunch(specFolder, mode));
						specFolder = project.getFolder("spec"); //$NON-NLS-1$
						if (specFolder != null && specFolder.exists())
							configs.add(findOrCreateLaunch(specFolder, mode));
						ILaunchConfiguration config = null;
						if (configs.size() == 1) {
							config = configs.get(0);
						} else if (configs.size() > 1) {
							config = chooseConfiguration(configs, mode);
						}
						if (config != null) {
							if (config.getAttribute(
									DLTKTestingConstants.ATTR_ENGINE_ID,
									(String) null) == null) {
								MessageDialog
										.openInformation(
												getShell(),
												Messages.RubyTestingLaunchShortcut_testLaunch,
												Messages.RubyTestingLaunchShortcut_theSelectedLaunchConfigurationDoesntHaveATestingEngineConfigured);
								return;
							}
							DebugUITools.launch(config, mode);
						}
						return;
					}
					case IModelElement.PROJECT_FRAGMENT:
					case IModelElement.SCRIPT_FOLDER: {
						performLaunch((IFolder) element.getResource(), mode);
						return;
					}
					case IModelElement.SOURCE_MODULE:
					case IModelElement.TYPE:
					case IModelElement.METHOD:
						elementToLaunch = element;
						break;
					}
				}
			}
			if (elementToLaunch == null) {
				showNoTestsFoundDialog();
				return;
			}
			performLaunch(elementToLaunch, mode);
		} catch (InterruptedException e) {
			// OK, silently move on
		} catch (CoreException e) {
			ExceptionHandler
					.handle(
							e,
							getShell(),
							Messages.RubyTestingLaunchShortcut_testLaunch,
							Messages.RubyTestingLaunchShortcut_testLaunchUnexpectedlyFailed);
		}
	}

	private void showNoTestsFoundDialog() {
		MessageDialog
				.openInformation(
						getShell(),
						Messages.RubyTestingLaunchShortcut_testLaunch,
						Messages.RubyTestingLaunchShortcut_unableToLocateAnyTestsInTheSpecifiedSelection);
	}

	private void performLaunch(IModelElement element, String mode)
			throws InterruptedException, CoreException {
		ILaunchConfigurationWorkingCopy temporary = createLaunchConfiguration(element);
		if (temporary == null) {
			return;
		}
		ILaunchConfiguration config = findExistingLaunchConfiguration(
				temporary, mode);
		if (config == null) {
			// no existing found: create a new one
			final IResource resource = element.getUnderlyingResource();
			if (resource != null) {
				temporary.setMappedResources(new IResource[] { resource });
			}
			config = temporary.doSave();
		} else {
			config = DLTKTestingMigrationDelegate.fixMappedResources(config);
		}
		if (config.getAttribute(DLTKTestingConstants.ATTR_ENGINE_ID,
				(String) null) == null) {
			MessageDialog
					.openInformation(
							getShell(),
							Messages.RubyTestingLaunchShortcut_testLaunch,
							Messages.RubyTestingLaunchShortcut_theSelectedLaunchConfigurationDoesntHaveATestingEngineConfigured);
			return;
		}
		DebugUITools.launch(config, mode);
	}

	private ILaunchConfiguration findOrCreateLaunch(IFolder folder, String mode)
			throws InterruptedException, CoreException {
		String name = folder.getName();
		String testName = name.substring(name.lastIndexOf(IPath.SEPARATOR) + 1);

		ILaunchConfigurationType configType = getLaunchManager()
				.getLaunchConfigurationType(getLaunchConfigurationTypeId());
		final ILaunchConfigurationWorkingCopy wc = configType.newInstance(null,
				getLaunchManager().generateLaunchConfigurationName(
						testName));

		wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				folder.getProject().getName());

		// wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_TEST_NAME,
		// EMPTY_STRING);
		IModelElement element = DLTKCore.create(folder);
		if (element != null) {
			wc.setAttribute(DLTKTestingConstants.ATTR_TEST_CONTAINER, element
					.getHandleIdentifier());
			// wc.setAttribute(ScriptLaunchConfigurationConstants.
			// ATTR_TEST_ELEMENT_NAME, EMPTY_STRING);

			final ITestingEngine[] engines = TestingEngineManager
					.getEngines(RubyNature.NATURE_ID);
			element.accept(new IModelElementVisitor() {

				private boolean detected;

				@Override
				public boolean visit(IModelElement element) {
					if (detected)
						return false;
					if (element instanceof ISourceModule) {
						TestingEngineDetectResult detection = TestingEngineManager
								.detect(engines, (ISourceModule) element);
						if (detection != null) {
							wc.setAttribute(
									DLTKTestingConstants.ATTR_ENGINE_ID,
									detection.getEngine().getId());
							detected = true;
							return false;
						}
					}
					return element instanceof IScriptFolder
							|| element instanceof IProjectFragment
							|| element instanceof IScriptProject;
				}

			});
		}

		wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_SCRIPT_NATURE,
				RubyNature.NATURE_ID);

		ILaunchConfiguration config = findExistingLaunchConfiguration(wc, mode);
		if (config == null) {
			// no existing found: create a new one
			wc.setMappedResources(new IResource[] { folder });
			config = wc.doSave();
		} else {
			config = DLTKTestingMigrationDelegate.fixMappedResources(config);
		}
		return config;
	}

	private void performLaunch(IFolder folder, String mode)
			throws InterruptedException, CoreException {
		ILaunchConfiguration config = findOrCreateLaunch(folder, mode);
		if (config.getAttribute(DLTKTestingConstants.ATTR_ENGINE_ID,
				(String) null) == null) {
			MessageDialog
					.openInformation(
							getShell(),
							Messages.RubyTestingLaunchShortcut_testLaunch,
							Messages.RubyTestingLaunchShortcut_theSelectedLaunchConfigurationDoesntHaveATestingEngineConfigured);
			return;
		}
		DebugUITools.launch(config, mode);
	}

	private Shell getShell() {
		return DLTKUIPlugin.getActiveWorkbenchShell();
	}

	private ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	/**
	 * Show a selection dialog that allows the user to choose one of the
	 * specified launch configurations. Return the chosen config, or
	 * <code>null</code> if the user cancelled the dialog.
	 * 
	 * @param configList
	 * @param mode
	 * @return ILaunchConfiguration
	 * @throws InterruptedException
	 */
	private ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList,
			String mode) throws InterruptedException {
		IDebugModelPresentation labelProvider = DebugUITools
				.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog
				.setTitle(Messages.RubyTestingLaunchShortcut_selectTestConfiguration);
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			dialog
					.setMessage(Messages.RubyTestingLaunchShortcut_selectConfigurationToDebug);
		} else {
			dialog
					.setMessage(Messages.RubyTestingLaunchShortcut_selectConfigurationToRun);
		}
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		throw new InterruptedException(); // cancelled by user
	}

	/**
	 * Returns the launch configuration type id of the launch configuration this
	 * shortcut will create. Clients can override this method to return the id
	 * of their launch configuration.
	 * 
	 * @return the launch configuration type id of the launch configuration this
	 *         shortcut will create
	 */
	protected String getLaunchConfigurationTypeId() {
		return "org.eclipse.dltk.ruby.testing.launchConfig"; //$NON-NLS-1$
	}

	/**
	 * Creates a launch configuration working copy for the given element. The
	 * launch configuration type created will be of the type returned by
	 * {@link #getLaunchConfigurationTypeId}. The element type can only be of
	 * type {@link IJavaProject}, {@link IPackageFragmentRoot},
	 * {@link IPackageFragment}, {@link IType} or {@link IMethod}.
	 * 
	 * Clients can extend this method (should call super) to configure
	 * additional attributes on the launch configuration working copy.
	 * 
	 * @return a launch configuration working copy for the given element
	 */
	protected ILaunchConfigurationWorkingCopy createLaunchConfiguration(
			IModelElement element) throws CoreException {
		String testFileName;

		String name = ScriptElementLabels.getDefault().getTextLabel(element,
				ScriptElementLabels.F_FULLY_QUALIFIED);
		String testName = name.substring(name.lastIndexOf(IPath.SEPARATOR) + 1);

		switch (element.getElementType()) {
		case IModelElement.SOURCE_MODULE:
		case IModelElement.TYPE: {
			testFileName = element.getResource().getProjectRelativePath()
					.toPortableString();
		}
			break;
		case IModelElement.METHOD: {
			testFileName = element.getResource().getProjectRelativePath()
					.toPortableString();
		}
			break;
		default:
			throw new IllegalArgumentException(
					"Invalid element type to create a launch configuration: " + element.getClass().getName()); //$NON-NLS-1$
		}

		ILaunchConfigurationType configType = getLaunchManager()
				.getLaunchConfigurationType(getLaunchConfigurationTypeId());
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null,
				getLaunchManager().generateLaunchConfigurationName(
						testName));

		wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				element.getScriptProject().getElementName());
		wc.setAttribute(
				ScriptLaunchConfigurationConstants.ATTR_MAIN_SCRIPT_NAME,
				testFileName);
		wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_SCRIPT_NATURE,
				RubyNature.NATURE_ID);
		wc.setAttribute(IDebugUIConstants.ATTR_CAPTURE_IN_CONSOLE, "true"); //$NON-NLS-1$
		// wc.setAttribute(XUnitLaunchConfigurationConstants.ATTR_TEST_NAME,
		// testFileName);
		// wc.setAttribute(ScriptLaunchConfigurationConstants.ATTR_CONTAINER_PATH
		// , containerHandleId);
		// wc.setAttribute(XUnitLaunchConfigurationConstants.
		// ATTR_TEST_ELEMENT_NAME, testElementName);
		// XUnitMigrationDelegate.mapResources(wc);
		ITestingEngine[] engines = TestingEngineManager
				.getEngines(RubyNature.NATURE_ID);
		ISourceModule module = (ISourceModule) element
				.getAncestor(IModelElement.SOURCE_MODULE);
		TestingEngineDetectResult detection = TestingEngineManager.detect(
				engines, module);
		if (detection != null) {
			wc.setAttribute(DLTKTestingConstants.ATTR_ENGINE_ID, detection
					.getEngine().getId());
		}

		return wc;
	}

	/**
	 * Returns the attribute names of the attributes that are compared when
	 * looking for an existing similar launch configuration. Clients can
	 * override and replace to customize.
	 * 
	 * @return the attribute names of the attributes that are compared
	 */
	protected String[] getAttributeNamesToCompare() {
		return new String[] {
				ScriptLaunchConfigurationConstants.ATTR_PROJECT_NAME,
				DLTKTestingConstants.ATTR_TEST_CONTAINER,
				ScriptLaunchConfigurationConstants.ATTR_MAIN_SCRIPT_NAME,
				// IDLTKTestingConstants.ENGINE_ID_ATR,
				ScriptLaunchConfigurationConstants.ATTR_SCRIPT_NATURE
		// XUnitLaunchConfigurationConstants.ATTR_TEST_NAME,
		// XUnitLaunchConfigurationConstants.ATTR_TEST_ELEMENT_NAME
		};
	}

	private static boolean hasSameAttributes(ILaunchConfiguration config1,
			ILaunchConfiguration config2, String[] attributeToCompare) {
		try {
			for (int i = 0; i < attributeToCompare.length; i++) {
				String val1 = config1.getAttribute(attributeToCompare[i],
						Util.EMPTY_STRING);
				String val2 = config2.getAttribute(attributeToCompare[i],
						Util.EMPTY_STRING);
				if (!val1.equals(val2)) {
					return false;
				}
			}
			return true;
		} catch (CoreException e) {
			// ignore access problems here, return false
		}
		return false;
	}

	private ILaunchConfiguration findExistingLaunchConfiguration(
			ILaunchConfigurationWorkingCopy temporary, String mode)
			throws InterruptedException, CoreException {
		ILaunchConfigurationType configType = temporary.getType();

		ILaunchConfiguration[] configs = getLaunchManager()
				.getLaunchConfigurations(configType);
		String[] attributeToCompare = getAttributeNamesToCompare();

		ArrayList<ILaunchConfiguration> candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
		for (int i = 0; i < configs.length; i++) {
			ILaunchConfiguration config = configs[i];
			if (hasSameAttributes(config, temporary, attributeToCompare)) {
				candidateConfigs.add(config);
			}
		}

		// If there are no existing configs associated with the IType, create
		// one.
		// If there is exactly one config associated with the IType, return it.
		// Otherwise, if there is more than one config associated with the
		// IType, prompt the
		// user to choose one.
		int candidateCount = candidateConfigs.size();
		if (candidateCount == 0) {
			return null;
		} else if (candidateCount == 1) {
			return candidateConfigs.get(0);
		} else {
			// Prompt the user to choose a config. A null result means the user
			// cancelled the dialog, in which case this method returns null,
			// since cancelling the dialog should also cancel launching
			// anything.
			ILaunchConfiguration config = chooseConfiguration(candidateConfigs,
					mode);
			if (config != null) {
				return config;
			}
		}
		return null;
	}

}
