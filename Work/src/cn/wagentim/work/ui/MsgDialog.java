package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MsgDialog {
	
	public static final int TYPE_INFO_DIALOG = 0;
	public static final int TYPE_CHOICE_DIALOG = 1;

	private Shell parent = null;
	private String text = null;
	private int type = TYPE_INFO_DIALOG;
	private String title = null;

	private Shell shell;

	private static final int SHELL_WIDTH = 400;
	private static final int SHELL_HEIGH = 130;

	private boolean choiceValue = false;

	public MsgDialog(final Shell parent, final String text, int type, Image image, final String title) {
		this.parent = parent;
		this.text = text;
		this.type = type;
		this.title = title;
	}

	public void show() {

		choiceValue = false;
		shell = new Shell(parent, SWT.BORDER | SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL);

		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		setCenter();
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 5;
		shell.setLayout(layout);
		parent.setEnabled(false);
		genText();
		
		if( TYPE_INFO_DIALOG == type )
		{
			genSingleBtn();
		}
		else
		{
			genTwoBtn();
		}
		
		addActions();

		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) {
				Display.getCurrent().sleep();
			}
		}

		shell.dispose();

		parent.setEnabled(true);
	}

	private void setCenter() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),
				(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}
	
//	private void genImage(){
//		labelImg = new Label(shell, SWT.NONE);
//		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
//		data.horizontalIndent = 20;
//		labelImg.setLayoutData(data);
//		labelImg.setImage(image);
//	}

	private void genText() {
		
		if (text != null && !text.equals("")) {
			labelTxt = new Label(shell, SWT.NONE);
			labelTxt.setText(text);
			GridData data = new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1);
			data.horizontalAlignment = GridData.CENTER;
			data.horizontalIndent = 10;
			data.verticalIndent = 8;
			labelTxt.setLayoutData(data);
		}
		
	}

	private void genSingleBtn() {
		
		if(null != title )
		{
			shell.setText(title);
		}else
		{
			shell.setText("Info Dialog");
		}

		btnOk = new Button(shell, SWT.PUSH);
		btnOk.setText("OK");

		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true,
				true, 2, 1);
		data.verticalIndent = 8;
		data.heightHint = 25;
		data.widthHint = 90;
		btnOk.setLayoutData(data);
	}

	private void genTwoBtn() {
		shell.setText("Choice Dialog");

		Composite buttonPane = new Composite(shell, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true,
				false, 2, 1);
		data.verticalIndent = 8;
		buttonPane.setLayout(layout);
		buttonPane.setLayoutData(data);

		data.horizontalIndent = 5;

		data = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		data.widthHint = 90;
		data.heightHint = 25;

		btnOk = new Button(buttonPane, SWT.PUSH);
		btnOk.setText("Yes");
		btnOk.setLayoutData(data);

		btnCancel = new Button(buttonPane, SWT.PUSH);
		btnCancel.setText("No");
		btnCancel.setLayoutData(data);
	}

	private void addActions() {

		if (btnCancel != null) {
			btnCancel.addListener(SWT.Selection, new Listener() 
			{

				@Override
				public void handleEvent(Event event) {
					shell.dispose();
				}

			});
		}

		btnOk.addListener(SWT.Selection, new Listener() 
		{

			@Override
			public void handleEvent(Event event) 
			{
				if( TYPE_INFO_DIALOG == type )
				{
					shell.dispose();
				}
			}

		});
	}

	public boolean getChoiceValue() {
		return choiceValue;
	}

	private Label labelTxt;
	private Button btnOk, btnCancel;

}
