package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.work.SheetEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.controller.IController;
import cn.wagentim.work.controller.SheetTicketController;
import cn.wagentim.work.controller.TicketController;
import cn.wagentim.work.filter.EGGQSelector;
import cn.wagentim.work.filter.RatingSelector;
import cn.wagentim.work.filter.TicketIDSelector;
import cn.wagentim.work.importer.Cluster8TicketImporter;
import cn.wagentim.work.importer.IImporter;
import cn.wagentim.work.listener.ICommentEditorListener;
import cn.wagentim.work.listener.ICompositeListener;
import cn.wagentim.work.listener.ISearchTableListener;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class MainWindow implements ISearchTableListener, ICompositeListener, ICommentEditorListener
{
	private static final float version = 0.1f;
	private static final String title = "KPM Ticket Viewer "+String.valueOf(version) + " HB";

	private static final int SHELL_WIDTH = 950;
	private static final int SHELL_HEIGH = 700;

	private static final GridData mainPaneGridData = new GridData(GridData.FILL, GridData.FILL, true, true, 3, 1);

	private Shell shell;
	
	private SashForm sashFormV; 

	private Composite statusBar;
	
	private TicketContentViewComposite contentViewerComposite;
	private DefaultSearchAndTableComposite listViewerComposite;
	private SheetManager sm = null;

	private IController controller = null;
	
	private Text statusBarContent;
	
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(MainWindow.class.getSimpleName())));
	
	private CommentsEditor commentsEditor = null;
	
	private List<IExternalComposite> externalComposite;
	
	private Menu mSheet;
	
	private List<MenuItem> sheetItems;
	
	public static final String[] FILE_EXTENDSION = new String[]{"*.xlsx"};
	
	public MainWindow()
	{
		externalComposite = new ArrayList<IExternalComposite>();
		sheetItems = new ArrayList<MenuItem>();
		controller = new TicketController();
	}
	// open the main window
	public void open()
	{
		final Display display = Display.getDefault();
		
		shell = new Shell();
		shell.setText(title);
		
		genMenubar();

		shell.setLayout(new GridLayout(1, false));
		genMainPane();
		setActions();
		genStatusBar();

		shell.setSize(SHELL_WIDTH, SHELL_HEIGH);
		setCenter();
		shell.open();

		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		
		display.dispose();
	}
	
	private void clearAllExternalComposites()
	{
//		for(IExternalComposite ec : externalComposite)
//		{
//			ec.dispose();
//		}
	}
	
	private void genFileMenu(final Menu menu)
	{
		final MenuItem miFile = new MenuItem(menu, SWT.CASCADE);
		miFile.setText("File");

		final Menu mFile = new Menu(miFile);
		miFile.setMenu(mFile);
		
		final MenuItem miLoadClu8 = new MenuItem(mFile, SWT.NONE);
		miLoadClu8.setText("Load Clu8 Tickets");
		miLoadClu8.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				if( null == controller || !(controller instanceof TicketController) );
				{
					controller = new TicketController();
				}
				updateTable(true);
			}
		});
		
		new MenuItem(mFile, SWT.SEPARATOR);
		
		final MenuItem mImportClu8 = new MenuItem(mFile, SWT.NONE);
		mImportClu8.setText("Import Clu8 Tickets");
		mImportClu8.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				IImporter importer = new Cluster8TicketImporter();
				importer.exec();
			}
		});
		
		new MenuItem(mFile, SWT.SEPARATOR);
		
		final MenuItem mExporter = new MenuItem(mFile, SWT.NONE);
		mExporter.setText("Export To Excel File");
		mExporter.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				if( null == listViewerComposite)
				{
					logger.log(Log.LEVEL_INFO, "The Seart Table Component is null");
					return;
				}
				
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setFilterExtensions(FILE_EXTENDSION);
				fd.setText(IConstants.STRING_SAVE_TO_EXCEL);
				fd.setFilterPath("c:\\");
				fd.setOverwrite(true);
				String targetLocation = fd.open();
				
				if( Validator.isNullOrEmpty(targetLocation) )
				{
					logger.log(Log.LEVEL_ERROR, "Cannot save the file to '" + targetLocation + "'");
					return;
				}
				
				saveTableContentToExcel(targetLocation);
			}
		});
		
		new MenuItem(mFile, SWT.SEPARATOR);
		
		final MenuItem mExit = new MenuItem(mFile, SWT.NONE);
		mExit.setText("Exit");
		mExit.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				existProgram();
			}
		});
	}
	
	private void existProgram()
	{
		if( null != shell )
		{
			shell.dispose();
		}
	}
	
	protected void saveTableContentToExcel(String targetLocation)
	{
		List<String> headers = listViewerComposite.getCurrentTableHeaders();
		List<List<String>> currentTableContent = listViewerComposite.getCurrentTableContent();
		
		controller.decorateOutput(headers, currentTableContent);
		
		controller.saveDataToExcelFile(targetLocation, headers, currentTableContent);
	}
	
	private void openCommentEditor()
	{
		if( null == commentsEditor )
		{
			commentsEditor = new CommentsEditor();
			externalComposite.add(commentsEditor);
			commentsEditor.setListener(this);
			commentsEditor.setCommentListener(this);
		}
		
		commentsEditor.setCurrKPMID(listViewerComposite.getSelectedTicketNumber());
		commentsEditor.open();
	}
		
	
	private void openSheetManager()
	{
		if( null == sm )
		{
			sm = new SheetManager();
			externalComposite.add(sm);
			sm.setListener(this);
			sm.open();
		}
	}
	
	public void updateTable(boolean loadDataFromDB)
	{
		updateTableHeaders();
		updateTableContent(loadDataFromDB);
	}
	
	public void updateTableContent(boolean loadDataFromDB)
	{
		List<String[]> content = controller.getTableContents(loadDataFromDB);
		listViewerComposite.updateTableContent(content);
		statusBarContent.setText(controller.getTotalDisplayedTicketNumber());
	}
	
	public void updateTableHeaders()
	{
		listViewerComposite.updateTableColumn(controller.getColumnHeaders());
	}
	
	private void genToolMenu(final Menu menu)
	{
		final MenuItem miFile = new MenuItem(menu, SWT.CASCADE);
		miFile.setText("Tool");

		final Menu mTool = new Menu(miFile);
		miFile.setMenu(mTool);
		
		final MenuItem miOpenCommentDialog = new MenuItem(mTool, SWT.NONE);
		miOpenCommentDialog.setText("Open Comment Dialog");
		miOpenCommentDialog.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				openCommentEditor();
			}
		});
	}
	
	private void genSheetMenu(final Menu menu)
	{
		final MenuItem miSheet = new MenuItem(menu, SWT.CASCADE);
		miSheet.setText("Sheet");

		mSheet = new Menu(miSheet);
		miSheet.setMenu(mSheet);
		
		final MenuItem miSheetManager = new MenuItem(mSheet, SWT.NONE);
		miSheetManager.setText("Sheet Manager");
		miSheetManager.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				if(null == sm)
				{
					openSheetManager();
				}
			}
		});
		
		new MenuItem(mSheet, SWT.SEPARATOR);
		
		loadSelfDefinedSheets();
	}
	
	public void loadSelfDefinedSheets()
	{
		clearMenuSheetItems();
		
		List<SheetEntity> sheets = controller.getAllSheets();
		
		if( !sheets.isEmpty() )
		{
			for(final SheetEntity s : sheets)
			{
				final MenuItem miSheet = new MenuItem(mSheet, SWT.NONE);
				miSheet.setText(s.getName());
				miSheet.addListener(SWT.Selection, new Listener()
				{
					@Override
					public void handleEvent(final Event event)
					{
						String dbName = s.getName() + IConstants.DB_SURFIX;
						
						if(controller instanceof SheetTicketController)
						{
							((SheetTicketController)controller).setSheet(dbName);
						}
						else
						{
							controller = new SheetTicketController(dbName);
						}
						
						updateTable(true);
					}
				});
				
				sheetItems.add(miSheet);
			}
		}
	}
	
	private void clearMenuSheetItems()
	{
		if( null != sheetItems)
		{
			for(MenuItem mi : sheetItems)
			{
				sheetItems.remove(mi);
				mi.dispose();
				mi = null;
			}
		}
	}
	private void genFilterMenu(final Menu menu)
	{
		final MenuItem miFilter = new MenuItem(menu, SWT.CASCADE);
		miFilter.setText("Filter");

		final Menu mFilter = new Menu(miFilter);
		miFilter.setMenu(mFilter);
		
		final MenuItem miRatingA = new MenuItem(mFilter, SWT.NONE);
		miRatingA.setText("Rating Selector");
		miRatingA.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.clearSelectors();
				controller.addSelectors(new RatingSelector());
				updateTableContent(true);
			}
		});

		final MenuItem mEGGQFilter = new MenuItem(mFilter, SWT.NONE);
		mEGGQFilter.setText("EG/GQ Ticket Filter");
		mEGGQFilter.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.clearSelectors();
				controller.addSelectors(new EGGQSelector());
				updateTableContent(true);
			}
		});
		
		new MenuItem(mFilter, SWT.SEPARATOR);
		
		final MenuItem mClearFilter = new MenuItem(mFilter, SWT.NONE);
		mClearFilter.setText("Clear All Filters");
		mClearFilter.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.clearSelectors();
				updateTableContent(true);
			}
		});
	}
	
	private void genMenubar()
	{
		final Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		genFileMenu(menu);
		genToolMenu(menu);
		genSheetMenu(menu);
		genFilterMenu(menu);
		genAboutMenu(menu);
	}

	private void genAboutMenu(Menu menu)
	{
		final MenuItem miFile = new MenuItem(menu, SWT.CASCADE);
		miFile.setText("About");

		final Menu mFile = new Menu(miFile);
		miFile.setMenu(mFile);
	}

	private void setCenter() {

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		shell.setLocation((int) ((dim.getWidth() - SHELL_WIDTH) / 2),
				(int) ((dim.getHeight() - SHELL_HEIGH) / 2));
	}
	
	private void genMainPane() {

		sashFormV = new SashForm(shell, SWT.VERTICAL);
		sashFormV.setLayoutData(mainPaneGridData);
		contentViewerComposite = new TicketContentViewComposite(sashFormV, SWT.NONE);
		listViewerComposite = new DefaultSearchAndTableComposite(sashFormV, SWT.NONE);
		listViewerComposite.setSearchTableListener(this);
		sashFormV.setWeights(new int[] { 1, 1 });
		
	}
	
	private void genStatusBar() {

		statusBar = new Composite(shell, SWT.NONE);
		final FillLayout layout = new FillLayout();
		statusBar.setLayout(layout);
		final Label label = new Label(statusBar, SWT.NONE);
		label.setText("Total Tickets: ");
		
		statusBarContent = new Text(statusBar, SWT.SINGLE);
		statusBarContent.setEditable(false);
		statusBarContent.setForeground(statusBarContent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
	}

	public void setActions()
	{
		shell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(final DisposeEvent arg0)
			{
				clearAllExternalComposites();
			}
		});

	}

	@Override
	public void selectedTicketNumber(int selectedTicketNumber)
	{
		TicketEntity selectedTicket = controller.getSelectedTicket(selectedTicketNumber);
		
		if( null == selectedTicket )
		{
			logger.log(Log.LEVEL_ERROR, "Cannot find ticket with the number: %1", String.valueOf(selectedTicketNumber));
		}
		
		contentViewerComposite.setSelectedTicket(selectedTicket);
		
		if((controller instanceof SheetTicketController) && ( null != commentsEditor) && (!commentsEditor.getShell().isDisposed()) )
		{
			commentsEditor.updateContent(((SheetTicketController)controller).getCommentsForCommentViewer(selectedTicketNumber));
		}
	}

	@Override
	public void compositeDispose(IExternalComposite externalComposite)
	{
		this.externalComposite.remove(externalComposite);
		if( externalComposite == commentsEditor )
		{
			commentsEditor = null;
		}
		else if( externalComposite == sm )
		{
			sm = null;
		}
	}
	@Override
	public void selectedSearchItem(String item)
	{
		controller.clearSelectors();
		
		if( StringConstants.EMPTY_STRING.equals(item) )
		{
			updateTableContent(true);
		}
		else if( IConstants.STRING_TICKET_ID.equals(item) )
		{
			controller.addSelectors(new TicketIDSelector());
		}
		
	}
	@Override
	public void setSearchContent(String content)
	{
		controller.setSearchContent(content);
		updateTableContent(false);
	}

	@Override
	public List<SheetEntity> getAllSheet()
	{
		return controller.getAllSheets();
	}
	
	@Override
	public void addTicketToSheet(String dbName, int kpmid)
	{
		controller.addTicketComment(dbName, kpmid);
	}
	
	@Override
	public void columnSelection(String columnName)
	{
		controller.columnSelected(columnName);
		updateTableContent(false);
	}
	@Override
	public void addComment(int kpmID, String[] data)
	{
		if( controller instanceof SheetTicketController )
		{
			SheetTicketController stc = ((SheetTicketController)controller); 
			stc.addComment(kpmID, data);
			commentsEditor.updateContent(stc.getCommentsForCommentViewer(kpmID));			
		}
	}
	@Override
	public boolean shouldShowDeleteTicketOption()
	{
		return controller instanceof SheetTicketController ? true : false;
	}
	@Override
	public void deleteSheetTicket(List<Integer> selectedTicketNumbers)
	{
		((SheetTicketController)controller).removeTicket(selectedTicketNumbers);
		updateTableContent(true);
	}
}