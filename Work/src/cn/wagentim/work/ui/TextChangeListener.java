package cn.wagentim.work.ui;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

import cn.wagentim.work.config.IConstants;

public class TextChangeListener implements Listener
{
	final private MenuItem mi;
	private boolean isSelected = false;
	private final String originalText;
	
	public TextChangeListener(final MenuItem mi)
	{
		this.mi = mi;
		this.originalText = mi.getText();
	}
	
	@Override
	public void handleEvent(Event event)
	{
		isSelected = !isSelected;
		
		String tmp = originalText;
		
		if( isSelected )
		{
			tmp = IConstants.STRING_MENU_ITEM_SELECTED_SYMBOL + tmp;
		}
		
		mi.setText(tmp);
	}

}
