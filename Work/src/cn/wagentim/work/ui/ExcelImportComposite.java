package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.config.TicketCommentConfigure;
import cn.wagentim.work.importer.ExcelFileImporter;

public class ExcelImportComposite implements IExternalComposite
{
	private Shell shell;
	private static final int SHELL_WIDTH = 600;
	private static final int SHELL_HEIGH = 250;
	private static final String DEFAULT_INDEX = "-1";

	private static final String title = "Excel Import Manager V0.1 HB";

	// ########################## GUI elements ####################################################

	private Label labelFileSelector;
	private Text txtFileSelector, txtSheetIndex, txtIgnoreLines, txtKPMIDIndex, txtCommentIndex, txtStatusIndex, txtPriorityIndex, txtNextStepIndex;
	private Button btnFileSelector, btnExec;
	
	private String sheetName;
	
	public ExcelImportComposite(String sheetName)
	{
		this.sheetName = sheetName;
	}
	
	public void open()
	{
		
		shell = new Shell();
		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		shell.setText(title);
		
		GridLayout layout = new GridLayout(1, true);
		shell.setLayout(layout);
		
		genFileSelector();
		genOptionPart();
		genBottomPart();
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

	private void genBottomPart()
	{
		Composite bottomPart = new Composite(shell, SWT.NONE);
		bottomPart.setLayout(new GridLayout(3, false));
		GridData gdfileSelector = new GridData(SWT.RIGHT, SWT.FILL, true, false);
		bottomPart.setLayoutData(gdfileSelector);
		
		btnExec = new Button(bottomPart, SWT.PUSH);
		btnExec.setText("Execute");
		GridData gdbtnExec = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		gdbtnExec.widthHint = 60;
		btnExec.setLayoutData(gdbtnExec);
		
		btnExec.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event arg0)
			{
				String fileLocation = txtFileSelector.getText();
				
				if( Validator.isNullOrEmpty(fileLocation) )
				{
					return;
				}
				
				int sheetIndex = Integer.valueOf(txtSheetIndex.getText());
				if( sheetIndex < 0 )
				{
					sheetIndex = -1;
				}
				
				int ignoreLines = Integer.valueOf(txtIgnoreLines.getText());
				if( ignoreLines < 0 )
				{
					ignoreLines = -1;
				}
				
				int kpmIdIndex = Integer.valueOf(txtKPMIDIndex.getText());
				if( kpmIdIndex < 0 )
				{
					kpmIdIndex = -1;
				}
				
				int commentIndex = Integer.valueOf(txtCommentIndex.getText());
				if( commentIndex < 0 )
				{
					commentIndex = -1;
				}
				
				int statusIndex = Integer.valueOf(txtStatusIndex.getText());
				if( statusIndex < 0 )
				{
					statusIndex = -1;
				}
				
				int priorityIndex = Integer.valueOf(txtPriorityIndex.getText());
				if( priorityIndex < 0 )
				{
					priorityIndex = -1;
				}
				
				int nextStepIndex = Integer.valueOf(txtNextStepIndex.getText());
				if( nextStepIndex < 0 )
				{
					nextStepIndex = -1;
				}
				
				TicketCommentConfigure configure = new TicketCommentConfigure();
				configure.setExcelFilePath(fileLocation);
				configure.setCommentColumnIndex(commentIndex);
				configure.setIgnoreLines(ignoreLines);
				configure.setKpmIdColumnIndex(kpmIdIndex);
				configure.setSheetDBName(sheetName + IConstants.DB_SURFIX);
				configure.setSheetIndex(sheetIndex);
				configure.setStatusColumnIndex(statusIndex);
				configure.setPriorityColumnIndex(priorityIndex);
				configure.setNextStepColumnIndex(nextStepIndex);
				
				new ExcelFileImporter(configure).exec();
			}
		});
	}

	private void genOptionPart()
	{
		Composite options = new Composite(shell, SWT.NONE);
		options.setLayout(new GridLayout(5, true));
		GridData gdoptions = new GridData(SWT.FILL, SWT.FILL, true, true);
		options.setLayoutData(gdoptions);
		
		final Group grpSheet = new Group(options, SWT.NONE);
		grpSheet.setText(IConstants.String_SHEET_INDEX);
		grpSheet.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtSheetIndex = new Text(grpSheet, SWT.SINGLE);
		txtSheetIndex.setText(DEFAULT_INDEX);
		GridData gdtxtSheetIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtSheetIndex.widthHint = 90;
		txtSheetIndex.setLayoutData(gdtxtSheetIndex);
		
		final Group grpIgnoreLines = new Group(options, SWT.NONE);
		grpIgnoreLines.setText(IConstants.String_IGNORE_LINES);
		grpIgnoreLines.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtIgnoreLines = new Text(grpIgnoreLines, SWT.SINGLE);
		txtIgnoreLines.setText(DEFAULT_INDEX);
		GridData gdtxtIgnoreLines = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtIgnoreLines.widthHint = 90;
		txtIgnoreLines.setLayoutData(gdtxtIgnoreLines);
		
		final Group grpKPMIDIndex = new Group(options, SWT.NONE);
		grpKPMIDIndex.setText(IConstants.String_KPM_ID_INDEX);
		grpKPMIDIndex.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtKPMIDIndex = new Text(grpKPMIDIndex, SWT.SINGLE);
		txtKPMIDIndex.setText(DEFAULT_INDEX);
		GridData gdtxtKPMIDIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtKPMIDIndex.widthHint = 90;
		txtKPMIDIndex.setLayoutData(gdtxtKPMIDIndex);
		
		final Group grpCommentIndex = new Group(options, SWT.NONE);
		grpCommentIndex.setText(IConstants.String_COMMENT_INDEX);
		grpCommentIndex.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtCommentIndex = new Text(grpCommentIndex, SWT.SINGLE);
		txtCommentIndex.setText(DEFAULT_INDEX);
		GridData gdtxtCommentIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtCommentIndex.widthHint = 90;
		txtCommentIndex.setLayoutData(gdtxtCommentIndex);
		
		final Group grpStatusIndex = new Group(options, SWT.NONE);
		grpStatusIndex.setText(IConstants.String_STATUS_INDEX);
		grpStatusIndex.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtStatusIndex = new Text(grpStatusIndex, SWT.SINGLE);
		txtStatusIndex.setText(DEFAULT_INDEX);
		GridData gdtxtStatusIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtCommentIndex.widthHint = 90;
		txtStatusIndex.setLayoutData(gdtxtStatusIndex);
		
		final Group grpPriorityIndex = new Group(options, SWT.NONE);
		grpPriorityIndex.setText(IConstants.String_PRIORITY_INDEX);
		grpPriorityIndex.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtPriorityIndex = new Text(grpPriorityIndex, SWT.SINGLE);
		txtPriorityIndex.setText(DEFAULT_INDEX);
		GridData gdtxtPriorityIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtPriorityIndex.widthHint = 90;
		txtPriorityIndex.setLayoutData(gdtxtPriorityIndex);
		
		final Group grpNextStepIndex = new Group(options, SWT.NONE);
		grpNextStepIndex.setText(IConstants.String_NEXT_STEP_INDEX);
		grpNextStepIndex.setLayout(TicketContentViewComposite.GRP_LAYOUT);
		txtNextStepIndex = new Text(grpNextStepIndex, SWT.SINGLE);
		txtNextStepIndex.setText(DEFAULT_INDEX);
		GridData gdtxtNextStepIndex = new GridData(SWT.FILL, SWT.FILL, true, true);
		gdtxtNextStepIndex.widthHint = 90;
		txtNextStepIndex.setLayoutData(gdtxtNextStepIndex);
		
	}

	private void genFileSelector()
	{
		Composite fileSelector = new Composite(shell, SWT.NONE);
		fileSelector.setLayout(new GridLayout(3, false));
		GridData gdfileSelector = new GridData(SWT.FILL, SWT.FILL, true, false);
		fileSelector.setLayoutData(gdfileSelector);
		
		labelFileSelector = new Label(fileSelector, SWT.NONE);
		labelFileSelector.setText("Excel Location");
		GridData gdlabelFileSelector = new GridData(SWT.FILL, SWT.FILL, false, true);
		gdlabelFileSelector.widthHint = 80;
		labelFileSelector.setLayoutData(gdlabelFileSelector);
		
		txtFileSelector = new Text(fileSelector, SWT.SINGLE | SWT.BORDER);
		GridData gdtxtFileSelector = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtFileSelector.setLayoutData(gdtxtFileSelector);
		
		btnFileSelector = new Button(fileSelector, SWT.PUSH);
		btnFileSelector.setText("Choose");
		GridData gdbtnFileSelector = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		gdbtnFileSelector.widthHint = 60;
		btnFileSelector.setLayoutData(gdbtnFileSelector);
		
		btnFileSelector.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event arg0)
			{
				openFileChooser();
			}
		});
		
	}

	protected void openFileChooser()
	{
		FileDialog dialog = new FileDialog(shell);
	    dialog.setFilterExtensions(MainWindow.FILE_EXTENDSION); // Windows
	    dialog.setFilterPath("c:\\"); // Windows path
		txtFileSelector.setText( dialog.open() );
	}

	private void setCenter() {

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),
				(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}

	@Override
	public void dispose()
	{
		if( null != shell )
		{
			shell.dispose();
		}
	}
}
