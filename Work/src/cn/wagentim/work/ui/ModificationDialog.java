package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class ModificationDialog
{

	private final Shell shell;

	private static final int SHELL_WIDTH = 700;
	private static final int SHELL_HEIGH = 400;

	private static final Color COLOR_BACKGROUD = Display.getCurrent()
			.getSystemColor(SWT.COLOR_WHITE);


	private Label lmn, lmnt, lmi, lmit, lmm, lmmt, lvp, lvpt;

	private Table table;

	private Button btnOk;

	public ModificationDialog(Shell shell)
	{
		this.shell = shell;
	}
	
	public void open(){

		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);

		shell.setBackground(COLOR_BACKGROUD);

		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 5;
		shell.setLayout(layout);

		final GridData grpGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);

		lmn = new Label(shell, SWT.NONE);
		lmn.setText("Model Name: ");
		lmnt = new Label(shell, SWT.None);
		lmnt.setLayoutData(grpGridData);
		lmn.setBackground(COLOR_BACKGROUD);
		lmnt.setBackground(COLOR_BACKGROUD);

		lmi = new Label(shell, SWT.NONE);
		lmi.setText("Model Guide ID: ");
		lmit = new Label(shell, SWT.None);
		lmit.setLayoutData(grpGridData);
		lmi.setBackground(COLOR_BACKGROUD);
		lmit.setBackground(COLOR_BACKGROUD);

		lmm = new Label(shell, SWT.NONE);
		lmm.setText("Module: ");
		lmmt = new Label(shell, SWT.None);
		lmmt.setLayoutData(grpGridData);
		lmm.setBackground(COLOR_BACKGROUD);
		lmmt.setBackground(COLOR_BACKGROUD);

		lvp = new Label(shell, SWT.NONE);
		lvp.setText("Dirty Projects: ");
		lvpt = new Label(shell, SWT.None);
		lvpt.setLayoutData(grpGridData);
		lvp.setBackground(COLOR_BACKGROUD);
		lvpt.setBackground(COLOR_BACKGROUD);
		lvp.setToolTipText("All projects, in which the previous changes have not been verified");

		table = new Table(shell, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		table.setHeaderVisible(true);

		final TableColumn type = new TableColumn(table, SWT.LEFT);
		type.setText("Time");
		type.setWidth(60);

		final TableColumn tcName = new TableColumn(table, SWT.LEFT);
		tcName.setText("Name");
		tcName.setWidth(140);

		final TableColumn tcType = new TableColumn(table, SWT.LEFT);
		tcType.setText("Type");
		tcType.setWidth(100);

		final TableColumn tcModule = new TableColumn(table, SWT.LEFT);
		tcModule.setText("Module");
		tcModule.setWidth(100);

		final TableColumn tcBuffered = new TableColumn(table, SWT.LEFT);
		tcBuffered.setText("Buffered");
		tcBuffered.setWidth(60);

		final TableColumn tcTerminal = new TableColumn(table, SWT.LEFT);
		tcTerminal.setText("Terminal");
		tcTerminal.setWidth(60);

		final TableColumn tcCore = new TableColumn(table, SWT.LEFT);
		tcCore.setText("IsCore");
		tcCore.setWidth(60);

		final TableColumn tcProject = new TableColumn(table, SWT.LEFT);
		tcProject.setText("Projects");
		tcProject.setWidth(120);

		final Composite buttonPane = new Composite(shell, SWT.NONE);
		layout = new GridLayout(1, false);
		GridData data = new GridData(GridData.END, GridData.CENTER, true, false, 2, 1);
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(data);
		buttonPane.setBackground(COLOR_BACKGROUD);

		data.horizontalIndent = 5;

		data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.widthHint = 90;
		data.heightHint = 25;

		btnOk = new Button(buttonPane, SWT.PUSH);
		btnOk.setText("OK");
		btnOk.setLayoutData(data);
		btnOk.setBackground(COLOR_BACKGROUD);
		btnOk.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event arg0)
			{
				shell.dispose();
			}
		});


		setCenter();

		shell.open();
		while (!shell.isDisposed())
		{
			if (!Display.getCurrent().readAndDispatch())
			{
				Display.getCurrent().sleep();
			}
		}

		shell.dispose();

	}


	private void setCenter()
	{
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}
}
