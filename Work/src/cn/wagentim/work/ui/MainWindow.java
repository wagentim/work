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

import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.work.SheetEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.controller.IController;
import cn.wagentim.work.controller.SheetTicketController;
import cn.wagentim.work.controller.TicketController;
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
	private static final int SHELL_WIDTH = 1024;
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
		shell.setText(IConstants.TITLE);
		
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
	
	/** File Main Items */
	private void genFileMenu(final Menu menu)
	{
		final Menu mFile = UIHelper.createTopMenu(menu, IConstants.MENU_FILE);
		
		final MenuItem miLoadClu8 = new MenuItem(mFile, SWT.NONE);
		miLoadClu8.setText(IConstants.LOAD_MAIN_TICKET);
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
		
//		final MenuItem miLoadNew = new MenuItem(mFile, SWT.NONE);
//		miLoadNew.setText("Load New Tickets");
//		miLoadNew.addListener(SWT.Selection, new Listener()
//		{
//			@Override
//			public void handleEvent(final Event event)
//			{
//				if( null == controller || !(controller instanceof TicketController) );
//				{
//					controller = new TicketController();
//				}
//				updateTable(true);
//			}
//		});
//		
//		new MenuItem(mFile, SWT.SEPARATOR);
		
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
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		
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
	
	private void genSupplierFilter(Menu mFilterSupplier)
	{
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_ESO_EB, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_ESO_EB);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_AW, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_AW);
				updateTableContent(false);
			}
		});
				
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_DELPHI, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_DELPHI);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_EECHINA, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_EECHINA);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_FP, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_FP);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLER_HARMAN, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER, IConstants.MENU_ITEM_SUPPLER_HARMAN);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuSeperator(mFilterSupplier);
		
		UIHelper.createMenuItem(mFilterSupplier, IConstants.MENU_ITEM_SUPPLIER_CLEAR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.removeSelector(IConstants.SELECTOR_SUPPLIER);
				updateTableContent(false);
			}
		});
	}
	
	private void genRatingFilter(Menu mFilterRating)
	{
		UIHelper.createMenuItem(mFilterRating, IConstants.MENU_ITEM_RATING_A, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_RATING, IConstants.STRING_RATING_A);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterRating, IConstants.MENU_ITEM_RATING_B, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_RATING, IConstants.STRING_RATING_B);
				updateTableContent(false);
			}
		});
				
		UIHelper.createMenuItem(mFilterRating, IConstants.MENU_ITEM_RATING_C, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_RATING, IConstants.STRING_RATING_C);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterRating, IConstants.MENU_ITEM_RATING_D, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_RATING, IConstants.STRING_RATING_D);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuSeperator(mFilterRating);
		
		UIHelper.createMenuItem(mFilterRating, IConstants.MENU_ITEM_RATING_CLEAR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.removeSelector(IConstants.SELECTOR_RATING);
				updateTableContent(false);
			}
		});
	}
	
	private void genSupplierStatusFilter(Menu mFilterSupplierStatus)
	{
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_EMPTY, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER_STATUS, IConstants.MENU_ITEM_SUPPLIER_STATUS_EMPTY);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_SOLVED, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER_STATUS, IConstants.MENU_ITEM_SUPPLIER_STATUS_SOLVED);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_TAKEN_OVER, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER_STATUS, IConstants.MENU_ITEM_SUPPLIER_STATUS_TAKEN_OVER);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_UNDER_WAY, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER_STATUS, IConstants.MENU_ITEM_SUPPLIER_STATUS_UNDER_WAY);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_VERIFICATION, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_SUPPLIER_STATUS, IConstants.MENU_ITEM_SUPPLIER_STATUS_VERIFICATION);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuSeperator(mFilterSupplierStatus);
		
		UIHelper.createMenuItem(mFilterSupplierStatus, IConstants.MENU_ITEM_SUPPLIER_STATUS_CLEAR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.removeSelector(IConstants.SELECTOR_SUPPLIER_STATUS);
				updateTableContent(false);
			}
		});
	}
	
	private void genEngineerStatusFilter(Menu mFilterEngineerStatus)
	{
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_0, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_0);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_1, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_1);
				updateTableContent(false);
			}
		});
				
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_2, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_2);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_3, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_3);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_4, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_4);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_5, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_5);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ES_6, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_ENGINEER_STATUS, IConstants.MENU_ITEM_ES_6);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuSeperator(mFilterEngineerStatus);
		
		UIHelper.createMenuItem(mFilterEngineerStatus, IConstants.MENU_ITEM_ENGINEER_STATUS_CLEAR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.removeSelector(IConstants.SELECTOR_ENGINEER_STATUS);
				updateTableContent(false);
			}
		});
	}
	
	private void genMarketFilter(Menu mFilterMarket)
	{
		UIHelper.createMenuItem(mFilterMarket, IConstants.MENU_ITEM_MARKET_CN, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_MARKET, IConstants.MENU_ITEM_MARKET_CN);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterMarket, IConstants.MENU_ITEM_MARKET_JP, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_MARKET, IConstants.MENU_ITEM_MARKET_JP);
				updateTableContent(false);
			}
		});
				
		UIHelper.createMenuItem(mFilterMarket, IConstants.MENU_ITEM_MARKET_KR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_MARKET, IConstants.MENU_ITEM_MARKET_KR);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuItem(mFilterMarket, IConstants.MENU_ITEM_MARKET_TW, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.addSearchContent(IConstants.SELECTOR_MARKET, IConstants.MENU_ITEM_MARKET_TW);
				updateTableContent(false);
			}
		});
		
		UIHelper.createMenuSeperator(mFilterMarket);
		
		UIHelper.createMenuItem(mFilterMarket, IConstants.MENU_ITEM_MARKET_CLEAR, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.removeSelector(IConstants.SELECTOR_MARKET);
				updateTableContent(false);
			}
		});
	}
	
	private void genFilterMenu(final Menu menu)
	{
		final Menu mFilter = UIHelper.createTopMenu(menu, IConstants.MENU_FILTER);
		
		final Menu mFilterRating = UIHelper.createTopMenu(mFilter, IConstants.MENU_FILTER_RATING);
		genRatingFilter(mFilterRating);
		
		final Menu mFilterSupplier = UIHelper.createTopMenu(mFilter, IConstants.MENU_FILTER_SUPPLIER);
		genSupplierFilter(mFilterSupplier);
		
		final Menu mFilterEngineerStatus = UIHelper.createTopMenu(mFilter, IConstants.MENU_FILTER_ENGINEER_STATUS);
		genEngineerStatusFilter(mFilterEngineerStatus);
		
		final Menu mFilterMarket = UIHelper.createTopMenu(mFilter, IConstants.MENU_FILTER_MARKET);
		genMarketFilter(mFilterMarket);
		
		final Menu mFilterSupplierStatus = UIHelper.createTopMenu(mFilter, IConstants.MENU_FILTER_SUPPLIER_STATUS);
		genSupplierStatusFilter(mFilterSupplierStatus);
		
		new MenuItem(mFilter, SWT.SEPARATOR);
		
		final MenuItem mClearFilter = new MenuItem(mFilter, SWT.NONE);
		mClearFilter.setText("Clear All Filters");
		mClearFilter.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				controller.clearSelectors();
				updateTableContent(false);
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
	public void selectedSearchItem(int selector, String item)
	{
		controller.addSearchContent(selector, item);
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