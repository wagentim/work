package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.jdo.Constants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class AboutDialog {

	private Shell shell;

	private static final int SHELL_WIDTH = 500;
	private static final int SHELL_HEIGH = 230;

	private static final Color COLOR_BACKGROUD = (Display.getCurrent() != null) ? Display.getCurrent().getSystemColor(SWT.COLOR_WHITE) : null;

	private static final String TITLE = "About";

	public void open(Shell parent) {

		shell = new Shell(parent, SWT.BORDER | SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);
		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		setCenter();

		GridLayout layout = new GridLayout(2, false);

		layout.marginLeft = 0;
		layout.marginTop = 0;
		layout.marginRight = 0;
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		shell.setLayout(layout);

		shell.setBackground(COLOR_BACKGROUD);

		shell.setText(TITLE);

		parent.setEnabled(false);

		generateImage();

		generateMain();

		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) {
				Display.getCurrent().sleep();
			}
		}

		shell.dispose();

		parent.setEnabled(true);
	}

	private void generateImage() {


	}

	private void setCenter() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),
				(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}

	private void generateMain() {

		Composite main = new Composite(shell, SWT.NONE);

		main.setLayout(new GridLayout(1, false));

		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true,
				true, 1, 1);

		data.minimumHeight = 20;
		main.setLayoutData(data);
		main.setBackground(COLOR_BACKGROUD);

		empty = new Label(main, SWT.None);

		name = new Label(main, SWT.None);
		name.setText(strName);
		name.setLayoutData(data);
		name.setBackground(COLOR_BACKGROUD);

		company = new Label(main, SWT.None);
		company.setText(strCompany);
		company.setLayoutData(data);
		company.setBackground(COLOR_BACKGROUD);

		time = new Label(main, SWT.None);
		time.setText(strTime);
		time.setLayoutData(data);
		time.setBackground(COLOR_BACKGROUD);

		userGuide = new Link(main, SWT.NONE);
		userGuide.setText(strUserGuide);
		userGuide.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Program.launch("User Guide.pdf");
			}
		});

		empty = new Label(main, SWT.None);

		data = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		data.widthHint = 90;
		data.heightHint = 25;

		button = new Button(main, SWT.None);
		button.setText("OK");
		button.setLayoutData(data);
		button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {

				shell.dispose();
			}
		});
	}

	private static final String strName = "Model Maker - Version ";
	private static final String strCompany = "e.solutions GmbH";
	private static final String strTime = "01.2015";
	private static final String strUserGuide = "<a>User Guide</a>";

	private Label name, company, empty, image, time;
	private Button button = null;
	private Link userGuide;
}
