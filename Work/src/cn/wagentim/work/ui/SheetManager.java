package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.work.Sheet;
import cn.wagentim.work.controller.SheetManagerController;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.listener.ICompositeListener;

public class SheetManager implements IExternalComposite
{
	
	private final Shell shell;

	private static final int SHELL_WIDTH = 700;
	private static final int SHELL_HEIGH = 400;

	private static final Color COLOR_BACKGROUD = Display.getCurrent()
			.getSystemColor(SWT.COLOR_WHITE);


	private Table table;

	private Button btnCreate;
	
	private Text txtSheetName;
	
	private ICompositeListener listener = null;
	
	private SheetManagerController controller = null;
	
	public SheetManager()
	{
		shell = new Shell();
		controller = new SheetManagerController();
	}
	
	public void setListener(ICompositeListener listener)
	{
		this.listener = listener;
	}
	
	public Shell getShell()
	{
		return shell;
	}
	
	public void open(){

		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		shell.setBackground(COLOR_BACKGROUD);
		shell.setText("Sheet Manager V0.1 HB");

		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		
		genTable();
		genBottomPanel();
		setActions();
		setCenter();
		loadData();

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

	private void loadData()
	{
		updateContent(controller.getTableContents(true));
	}

	private void setActions()
	{
		shell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(final DisposeEvent arg0)
			{
				dispose();
			}
		});
		
		table.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent se)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void genBottomPanel()
	{
		final Composite bottonPane = new Composite(shell, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		bottonPane.setLayout(layout);
		bottonPane.setBackground(COLOR_BACKGROUD);
		GridData gdBottonPane = new GridData(SWT.FILL, SWT.FILL, true, false);
		bottonPane.setLayoutData(gdBottonPane);
		
		txtSheetName = new Text(bottonPane, SWT.SINGLE | SWT.BORDER );
		txtSheetName.setEditable(true);
		GridData gdComment = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtSheetName.setLayoutData(gdComment);

		btnCreate = new Button(bottonPane, SWT.PUSH);
		btnCreate.setText("Create");
		btnCreate.setBackground(COLOR_BACKGROUD);
		GridData gdBtn = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		gdBtn.widthHint = 60;
		btnCreate.setLayoutData(gdBtn);
		btnCreate.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event arg0)
			{
				String name = txtSheetName.getText();
				
				if( Validator.isNullOrEmpty(name) )
				{
					return;
				}
				
				Sheet s = new Sheet();
				s.setName(name);
				s.setTime(System.currentTimeMillis());
				
				controller.updateRecord(s);
				
				loadData();
				
				txtSheetName.setText(StringConstants.EMPTY_STRING);
			}
		});
	}

	private void genTable()
	{
		table = new Table(shell, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		table.setHeaderVisible(true);
		
		Header[] headers = controller.getColumnHeaders();
		
		for(int i = 0; i < headers.length; i++)
		{
			Header header = headers[i];
			final TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText(header.getName());
			tc.setWidth(header.getWidth());
		}
	}
	
	public void dispose()
	{
		if( null != listener )
		{
			listener.compositeDispose(this);
			listener = null;
		}
		shell.dispose();
	}
	private void setCenter()
	{
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}
	
	public void updateContent(List<String[]> contents)
	{
		if( contents.isEmpty() )
		{
			return;
		}
		
		table.removeAll();
		
		int count = 0;
		
		for(String[] s : contents)
		{
			final TableItem item = new TableItem(table, SWT.None);
			item.setText(s);
			count++;
			if (count % 2 != 0)
			{
				item.setBackground(new Color(table.getDisplay(), 230, 235, 249));
			}
		}
	}
}
