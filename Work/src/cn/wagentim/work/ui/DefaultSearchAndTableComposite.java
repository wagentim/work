package cn.wagentim.work.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.SheetEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.listener.ISearchTableListener;

public class DefaultSearchAndTableComposite extends Composite
{
	protected Table table;
	private ISearchTableListener searchTableListener = null;
	private Text searchText;
	private Combo comboBox;
	private static final String[] SEARCH_ITEM = {StringConstants.EMPTY_STRING, IConstants.STRING_TICKET_ID, IConstants.STRING_SHORT_TEXT};
		
	public DefaultSearchAndTableComposite(Composite parent, int style)
	{
		super(parent, style);
		init(parent);
		setActions();
	}
	
	public void setSearchTableListener(ISearchTableListener listener)
	{
		this.searchTableListener = listener;
	}
	
	public void updateTableContent(List<String[]> contents)
	{
		table.removeAll();

		if( contents.isEmpty() )
		{
			return;
		}
		
		
		int count = 0;
		
		for (String[] s : contents)
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
	
	protected void init(Composite parent)
	{
		// create drop-down combo box to show all possible search items
		final GridLayout resultLayout = new GridLayout(3, false);
		this.setLayout(resultLayout);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label label = new Label(this, SWT.BOLD);
		label.setText("Search: ");
		
		comboBox = new Combo(this, SWT.DROP_DOWN | SWT.BORDER);
		
		for(int i = 0; i < SEARCH_ITEM.length; i++)
		{
			comboBox.add(SEARCH_ITEM[i]);
		}
		
		searchText = new Text(this, SWT.SINGLE | SWT.BORDER);
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		table = new Table(this, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.BORDER);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table.setHeaderVisible(true);
		
	}
	
	public Table getTable()
	{
		return table;
	}

	public void updateTableColumn(Header[] columnHeaders)
	{
		deleteAllColumns();
		int j;
		if((j = columnHeaders.length) > 0)
		{
			for(int i = 0; i < j; i++)
			{
				final TableColumn tc = new TableColumn(table, SWT.LEFT);
				final String columnName = columnHeaders[i].getName();
				tc.setText(columnName);
				tc.setWidth(columnHeaders[i].getWidth());
				tc.setResizable(true);
				tc.addSelectionListener(new SelectionListener()
				{
					
					@Override
					public void widgetSelected(SelectionEvent arg0)
					{
						searchTableListener.columnSelection(columnName);
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0)
					{
						
					}
				});
			}
		}
	}
	
	public int getSelectedTicketNumber()
	{
		int index = table.getSelectionIndex();
		
		if( index < 0 )
		{
			return -1;
		}
		
		TableItem item = table.getItem(index);

		if( null != item )
		{
		 return Integer.valueOf(item.getText(0));
		}
		else
		{
			return -1;
		}
	}
	
	public List<Integer> getSelectedTickets()
	{
		List<Integer> result = new ArrayList<Integer>();
		
		TableItem[] selectedItems = table.getSelection();
		
		if(null != selectedItems )
		{
			for(TableItem ti : selectedItems)
			{
				result.add(Integer.valueOf(ti.getText(0)));
			}
		}
		
		return result;
	}
	
	private void setActions()
	{
		table.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseUp(final MouseEvent e) {}

			@Override
			public void mouseDown(final MouseEvent e)
			{
				if (e.button == 3)
				{
					final Point pt = new Point(e.x, e.y);
					final TableItem item = table.getItem(pt);

					if (item == null)
					{
						return;
					}
					handleSelectedTicket();
					createPopup();
				}
				else if (e.button == 1)
				{
					handleSelectedTicket();
				}

				if( table.getSelection().length <= 0 )
				{
					return;
				}
			}

			@Override
			public void mouseDoubleClick(final MouseEvent e)
			{
				if (table == null || table.getSelectionCount() <= 0)
				{
					return;
				}

				if (e.button == 1)
				{
				}
			}
		});

		table.addKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(final org.eclipse.swt.events.KeyEvent event)
			{
				final int keyCode = event.keyCode;

				if( keyCode == SWT.ARROW_DOWN || keyCode == SWT.ARROW_UP )
				{
					handleSelectedTicket();
				}
			}

			@Override
			public void keyPressed(final org.eclipse.swt.events.KeyEvent arg0) {}
		});
		
		searchText.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(final ModifyEvent e)
			{
				if (table == null)
				{
					return;
				}
				
				searchTableListener.setSearchContent(searchText.getText());
			}
		});
		
		comboBox.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent se)
			{
				if( StringConstants.EMPTY_STRING.equals(comboBox.getText()))
				{
					searchText.setText(StringConstants.EMPTY_STRING);
				}
				searchTableListener.selectedSearchItem(comboBox.getText());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void handleSelectedTicket()
	{
		if(table.getSelectionIndex() < 0)
		{
			return;
		}
		
		int selectedTicketNumber = getSelectedTicketNumber();
		
		if(selectedTicketNumber < 0)
		{
			return;
		}
		
		searchTableListener.selectedTicketNumber(selectedTicketNumber);
	}
	
	private void deleteAllColumns()
	{
		while ( table.getColumnCount() > 0 ) 
		{
		    table.getColumns()[0].dispose();
		}
	}
	
	private void createPopup()
	{
		final Menu menu = new Menu(table.getShell(), SWT.POP_UP);
		
		if( searchTableListener.shouldShowDeleteTicketOption())
		{
			final MenuItem miDeleteTicket = new MenuItem(menu, SWT.CASCADE);
			miDeleteTicket.setText("Delete Ticket");
			miDeleteTicket.addSelectionListener(new SelectionListener()
			{
				
				@Override
				public void widgetSelected(SelectionEvent arg0)
				{
					searchTableListener.deleteSheetTicket(getSelectedTickets());
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}
			});
			
			new MenuItem(menu, SWT.SEPARATOR);
		}

		List<SheetEntity> sheets = searchTableListener.getAllSheet();
		
		for( final SheetEntity s : sheets)
		{
			final MenuItem mi = new MenuItem(menu, SWT.CASCADE);
			mi.setText("Add to " + s.getName());
			mi.addSelectionListener(new SelectionListener()
			{
				@Override
				public void widgetSelected(final SelectionEvent arg0)
				{
					TableItem[] selectedItems = table.getSelection();
					
					if( null != selectedItems && selectedItems.length > 0 )
					{
						String dbName = s.getName();
						String dbFullName = dbName + IConstants.DB_SURFIX;
						
						for(int i = 0; i < selectedItems.length; i++)
						{
							int kpmid = Integer.valueOf(selectedItems[i].getText(0));
							searchTableListener.addTicketToSheet(dbFullName, kpmid);
						}
						
						StringBuffer sb = new StringBuffer("Add ");
						sb.append(selectedItems.length);
						sb.append(" tickets to the Sheet: ");
						sb.append(dbName);
						
						new MsgDialog(table.getShell(), sb.toString(), MsgDialog.TYPE_INFO_DIALOG, null, "Copy Tickets").show();
					}
					
				}
				
				@Override
				public void widgetDefaultSelected(final SelectionEvent arg0)
				{
				}
			});
		}
		
		final Point pt = Display.getCurrent().getCursorLocation();
		menu.setLocation(pt.x, pt.y);
		menu.setVisible(true);
	}

	public List<String> getCurrentTableHeaders()
	{
		List<String> result = new ArrayList<String>();
		
		if( null == table )
		{
			return result;
		}
		
		TableColumn[] columns = table.getColumns();
		
		if( null == columns || columns.length <= 0 )
		{
			return result;
		}
		
		
		for(int i = 0; i < columns.length; i++)
		{
			result.add(columns[i].getText());
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> getCurrentTableContent()
	{
		if( null == table )
		{
			return Collections.EMPTY_LIST;
		}
		
		int rows = table.getItemCount();
		
		if( rows <= 0 )
		{
			return Collections.EMPTY_LIST;
		}
		
		List<List<String>> result = new ArrayList<List<String>>();
		
		TableItem[] items = table.getItems();
		int columns = table.getColumnCount();
		
		for(int i = 0; i < rows; i++)
		{
			List<String> data = new ArrayList<String>();
			for(int j = 0; j < columns; j++)
			{
				data.add(items[i].getText(j));
			}
			
			result.add(data);
		}
		
		return result;
	}

 }
