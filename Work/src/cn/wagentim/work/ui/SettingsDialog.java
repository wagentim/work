package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SettingsDialog
{
	private Shell shell;
	private final Shell parent;

	private static final int SHELL_WIDTH = 800;
	private static final int SHELL_HEIGH = 550;

	private static final String title = "Settings";

	// ########################## GUI elements ####################################################

	private Label dbAddr, dbDriver, dbPort, dbProtocol, dbName, dbNameSDIS, dbParameter,
			dbLoginName, dbLoginPass, modules, modulesSDIS, targetModulesSDIS, modelTypesSDIS;
	private Text dbAddrT, dbDriverT, dbPortT, dbProtocolT, dbNameT, dbNameSDIST,
			dbParameterT, dbLoginNameT, dbLoginPassT, modulesT, modulesSDIST, targetModulesSDIST, modelTypesSDIST;

	private Button btnOk, btnCancel;

	public SettingsDialog(final Shell parent)
	{
		this.parent = parent;
	}

	public void open()
	{
//		parent.setEnabled(false);
//
//		shell = new Shell(parent, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX | SWT.RESIZE);
//		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
//		shell.setText(title);
//
//		shell.setImage(Utility.loadImage(Constants.ICON_TITLE));
//		setCenter();
//		GridLayout layout = new GridLayout(2, false);
//		layout.verticalSpacing = 5;
//		shell.setLayout(layout);
//
//		final GridData grpGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
//
//		dbDriver = new Label(shell, SWT.NONE);
//		dbDriver.setText("DB Driver");
//		dbDriverT = new Text(shell, SWT.BORDER);
//		dbDriverT.setLayoutData(grpGridData);
//		dbDriverT.setEnabled(false);
//
//		dbProtocol = new Label(shell, SWT.NONE);
//		dbProtocol.setText("DB Protocol");
//		dbProtocolT = new Text(shell, SWT.BORDER);
//		dbProtocolT.setLayoutData(grpGridData);
//		dbProtocolT.setEnabled(false);
//
//		dbAddr = new Label(shell, SWT.NONE);
//		dbAddr.setText("DB Address: ");
//		dbAddrT = new Text(shell, SWT.BORDER);
//		dbAddrT.setLayoutData(grpGridData);
//		dbAddrT.setEnabled(false);
//
//		dbPort = new Label(shell, SWT.NONE);
//		dbPort.setText("DB Port");
//		dbPortT = new Text(shell, SWT.BORDER);
//		dbPortT.setLayoutData(grpGridData);
//		dbPortT.setEnabled(false);
//
//		dbName = new Label(shell, SWT.NONE);
//		dbName.setText("DB Name");
//		dbNameT = new Text(shell, SWT.BORDER);
//		dbNameT.setLayoutData(grpGridData);
//		if (!Controller.isESO || Controller.onlyGlobalSettings()) {
//			dbNameT.setEnabled(false);
//		}
//
//		dbNameSDIS = new Label(shell, SWT.NONE);
//		dbNameSDIS.setText("DB Name SDIS");
//		dbNameSDIST = new Text(shell, SWT.BORDER);
//		dbNameSDIST.setLayoutData(grpGridData);
//		if (!Controller.isESO || Controller.onlyGlobalSettings()) {
//			dbNameSDIST.setEnabled(false);
//		}
//
//		dbParameter = new Label(shell, SWT.NONE);
//		dbParameter.setText("DB Parameter");
//		dbParameterT = new Text(shell, SWT.BORDER);
//		dbParameterT.setLayoutData(grpGridData);
//		if (!Controller.isESO || Controller.onlyGlobalSettings()) {
//			dbParameterT.setEnabled(false);
//		}
//
//		dbLoginName = new Label(shell, SWT.NONE);
//		dbLoginName.setText("Login Name");
//		dbLoginNameT = new Text(shell, SWT.BORDER);
//		dbLoginNameT.setLayoutData(grpGridData);
//		if (!Controller.isESO || Controller.onlyGlobalSettings()) {
//			dbLoginNameT.setEnabled(false);
//		}
//
//		dbLoginPass = new Label(shell, SWT.NONE);
//		dbLoginPass.setText("Login Password");
//		dbLoginPassT = new Text(shell, SWT.BORDER | SWT.PASSWORD);
//		dbLoginPassT.setLayoutData(grpGridData);
//		if (!Controller.isESO || Controller.onlyGlobalSettings()) {
//			dbLoginPassT.setEnabled(false);
//		}
//
//		modules = new Label(shell, SWT.NONE);
//		modules.setText("Modules");
//		modulesT = new Text(shell, SWT.BORDER);
//		modulesT.setLayoutData(grpGridData);
//
//		modulesSDIS = new Label(shell, SWT.NONE);
//		modulesSDIS.setText("Modules SDIS");
//		modulesSDIST = new Text(shell, SWT.BORDER);
//		modulesSDIST.setLayoutData(grpGridData);
//
//		targetModulesSDIS = new Label(shell, SWT.NONE);
//		targetModulesSDIS.setText("Target modules SDIS");
//		targetModulesSDIST = new Text(shell, SWT.BORDER);
//		targetModulesSDIST.setLayoutData(grpGridData);
//
//		modelTypesSDIS = new Label(shell, SWT.NONE);
//		modelTypesSDIS.setText("Model types SDIS");
//		modelTypesSDIST = new Text(shell, SWT.BORDER);
//		modelTypesSDIST.setLayoutData(grpGridData);
//
//		final Composite buttonPane = new Composite(shell, SWT.NONE);
//		layout = new GridLayout(2, false);
//		GridData data = new GridData(GridData.END, GridData.CENTER, true,
//				false, 2, 1);
//		buttonPane.setLayout(layout);
//		buttonPane.setLayoutData(data);
//
//		data.horizontalIndent = 5;
//
//		data = new GridData(GridData.END, GridData.CENTER, false, false);
//		data.widthHint = 90;
//		data.heightHint = 25;
//
//		btnOk = new Button(buttonPane, SWT.PUSH);
//		btnOk.setText("Save");
//		btnOk.setLayoutData(data);
//		btnOk.setEnabled(Controller.isESO);
//
//		btnCancel = new Button(buttonPane, SWT.PUSH);
//		btnCancel.setText("Cancel");
//		btnCancel.setLayoutData(data);
//
//		fillData();
//
//		initActions();
//
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!Display.getCurrent().readAndDispatch()) {
//				Display.getCurrent().sleep();
//			}
//		}
//
//		shell.dispose();
//
//		parent.setEnabled(true);
	}

	private void initActions() {

		btnCancel.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				shell.dispose();
			}

		});

		btnOk.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {

				okAction();

				shell.dispose();
			}

		});

		dbNameT.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		dbNameSDIST.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		dbParameterT.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		dbLoginNameT.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		dbLoginPassT.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		modulesT.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		modulesSDIST.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		targetModulesSDIST.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});

		modelTypesSDIST.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(final TraverseEvent paramTraverseEvent) {
				if(paramTraverseEvent.detail == SWT.TRAVERSE_RETURN)
				{
					okAction();

					shell.dispose();
				}

			}
		});
	}

	private void okAction() {


//		try {
//			Utility.saveSettings(settings);
//		} catch (final IOException e) {
//			e.printStackTrace();
//		}
	}

	private void setCenter() {

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),
				(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}

	public void fillData()
	{
	}
}
