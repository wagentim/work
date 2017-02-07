package de.eso.modelmaker.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cn.wagentim.work.entity.Header;
import cn.wagentim.work.listener.ISearchTableListener;

public class DefaultSearchAndTableComposite extends Composite
{
	protected Table table;
	private ISearchTableListener searchTableListener = null;
	
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
		if( contents.isEmpty() )
		{
			return;
		}
		
		table.removeAll();
		
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
		
		Combo comboBox = new Combo(this, SWT.DROP_DOWN | SWT.BORDER);
		
//		String[] searchItems = getSearchItems();
//		
//		for(int i = 0; i < searchItems.length; i++)
//		{
//			comboBox.add(searchItems[i]);
//		}
		
		Text searchText = new Text(this, SWT.SINGLE | SWT.BORDER);
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
				tc.setText(columnHeaders[i].getName());
				tc.setWidth(columnHeaders[i].getWidth());
				tc.setResizable(true);
			}
		}
	}
	
	private int getSelectedTicketNumber()
	{
		return Integer.valueOf(table.getItem(table.getSelectionIndex()).getText(0));
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
 }
