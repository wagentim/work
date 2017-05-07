package cn.wagentim.work.ui;

import javax.transaction.NotSupportedException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cn.wagentim.basicutils.StringConstants;

public class SelectionIndicationMenuItem extends MenuItem
{

	private boolean isSelected = false;
	private static final String SELECTED_MARK = "* ";
	private String text = StringConstants.EMPTY_STRING;
	
	private void updateText(String text)
	{
		super.setText(text);
	}
	
	protected void checkSubclass()
	{
		
	}
	
	public void setText(String text)
	{
		super.setText(text);
		this.text = text;
		isSelected = false;
	}
	
	public SelectionIndicationMenuItem(Menu parent, int style, final IMenuItemSelectionListener listener)
	{
		super(parent, style);
		checkSubclass();
		
		super.addListener(SWT.Selection, new Listener()
		{
			
			@Override
			public void handleEvent(Event arg0)
			{
				isSelected = !isSelected;

				if (isSelected)
				{
					updateText(SELECTED_MARK + text);
				} 
				else
				{
					updateText(text);
				}

				listener.exec(isSelected);
			}
		});
	}
	
	public void addSelectionListener(SelectionListener listener)
	{
		try
		{
			throw new NotSupportedException("addSelectionListener is not supported");
		} 
		catch (NotSupportedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addListener(int eventType, Listener listener)
	{
		try
		{
			throw new NotSupportedException("addListener is not supported");
		} 
		catch (NotSupportedException e)
		{
			e.printStackTrace();
		}
	}
	
}