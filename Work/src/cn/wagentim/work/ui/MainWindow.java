package cn.wagentim.work.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.controller.IController;
import cn.wagentim.work.controller.MustFixController;
import cn.wagentim.work.controller.RawDataController;
import cn.wagentim.work.listener.ICommentEditorListener;
import cn.wagentim.work.listener.ISearchTableListener;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class MainWindow implements ISearchTableListener, ICommentEditorListener
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

	private IController controller = null;
	
	private Text statusBarContent;
	
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(MainWindow.class.getSimpleName())));
	
	private CommentsEditor commentsEditor = null;

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
//		setCenter();
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
				if( null == controller || !(controller instanceof MustFixController) );
				{
					controller = new RawDataController();
				}
				updateTable(true);
				statusBarContent.setText(controller.getTotalDisplayedTicketNumber());
			}
		});

		final MenuItem miLoadMustFix = new MenuItem(mFile, SWT.NONE);
		miLoadMustFix.setText("Load Clu8 Must Fix");
		miLoadMustFix.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				if( null == controller || !(controller instanceof MustFixController) );
				{
					controller = new MustFixController();
				}
				updateTable(true);
				statusBarContent.setText(controller.getTotalDisplayedTicketNumber());
				
				openCommentEditor();
			}
		});
	}
	
	private void openCommentEditor()
	{
		if( null != commentsEditor )
		{
			commentsEditor.dispose();
		}
		
		commentsEditor = new CommentsEditor();
		commentsEditor.setListener(this);
		commentsEditor.open();
	}
	
	private void updateTable(boolean loadDataFromDB)
	{
		listViewerComposite.updateTableColumn(controller.getColumnHeaders());
		listViewerComposite.updateTableContent(controller.getTableContents(loadDataFromDB));
	}
	
	private void genToolMenu(final Menu menu)
	{
		final MenuItem miFile = new MenuItem(menu, SWT.CASCADE);
		miFile.setText("Tool");

		final Menu mFile = new Menu(miFile);
		miFile.setMenu(mFile);
		
		final MenuItem miLoadClu8 = new MenuItem(mFile, SWT.NONE);
		miLoadClu8.setText("Import Clu8 Tickets");
		miLoadClu8.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
			}
		});

		final MenuItem miLoadMustFix = new MenuItem(mFile, SWT.NONE);
		miLoadMustFix.setText("Import Clu8 Must Fix Ticket");
		miLoadMustFix.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				
			}
		});
	}
	
	private void genFilterMenu(final Menu menu)
	{
		final MenuItem miFilter = new MenuItem(menu, SWT.CASCADE);
		miFilter.setText("Filter");

		final Menu mFilter = new Menu(miFilter);
		miFilter.setMenu(mFilter);
		
		final MenuItem miRatingA = new MenuItem(mFilter, SWT.NONE);
		miRatingA.setText("Rating A");
		miRatingA.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
				
			}
		});

		final MenuItem miLoadMustFix = new MenuItem(mFilter, SWT.NONE);
		miLoadMustFix.setText("EG Ticket");
		miLoadMustFix.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(final Event event)
			{
			}
		});
	}
	
	private void genMenubar()
	{
		final Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		genFileMenu(menu);
		genToolMenu(menu);
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
				if( null != commentsEditor )
				{
					commentsEditor.dispose();
					commentsEditor = null;
				}
			}
		});

	}

	@Override
	public void selectedTicketNumber(int selectedTicketNumber)
	{
		Ticket selectedTicket = controller.getSelectedTicket(selectedTicketNumber);
		if( null == selectedTicket )
		{
			logger.log(Log.LEVEL_ERROR, "Cannot find ticket with the number: %1", String.valueOf(selectedTicketNumber));
		}
		
		contentViewerComposite.setSelectedTicket(selectedTicket);
		
		if((controller instanceof MustFixController) && ( null != commentsEditor) )
		{
			commentsEditor.updateContent(((MustFixController)controller).getComments(selectedTicketNumber));
		}
	}

	@Override
	public void commentEditorDispose()
	{
		commentsEditor = null;
	}
}