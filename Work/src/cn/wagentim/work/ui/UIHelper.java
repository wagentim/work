package cn.wagentim.work.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;

public class UIHelper
{
	public static SelectionIndicationMenuItem createMenuItem(final Menu menu, final String name, final IMenuItemSelectionListener listener)
	{
		final SelectionIndicationMenuItem menuItem = new SelectionIndicationMenuItem(menu, SWT.NONE, listener);
		menuItem.setText(Validator.isNullOrEmpty(name) ? StringConstants.EMPTY_STRING : name);
		return menuItem;
	}
	
	public static MenuItem createMenuItem(final Menu menu, final String name, final Listener listener)
	{
		final MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText(Validator.isNullOrEmpty(name) ? StringConstants.EMPTY_STRING : name);
		menuItem.addListener(SWT.Selection, listener);
		return menuItem;
	}
	
	public static MenuItem createMenuItem(final Menu menu, final String name)
	{
		final MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText(Validator.isNullOrEmpty(name) ? StringConstants.EMPTY_STRING : name);
		return menuItem;
	}
	
	public static MenuItem createMenuSeperator(final Menu menu)
	{
		return new MenuItem(menu, SWT.SEPARATOR);
	}
	
	public static Menu createTopMenu(final Menu menuBar, final String name)
	{
		final MenuItem menuItem = new MenuItem(menuBar, SWT.CASCADE);
		menuItem.setText(Validator.isNullOrEmpty(name) ? StringConstants.EMPTY_STRING : name);

		final Menu menu = new Menu(menuItem);
		menuItem.setMenu(menu);
		
		return menu;
	}
}
