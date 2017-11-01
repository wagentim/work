package cn.wagentim.work.ui;

import cn.wagentim.work.event.EventManager;
import cn.wagentim.work.event.IEvent;
import cn.wagentim.work.event.IEventComponent;

/**
 * Super Class for all widgets
 * 
 * @author EHUABI0
 *
 */
public abstract class AbstractWidget implements IWidget, IEventComponent
{
	public AbstractWidget()
	{
		EventManager.registComponent(this);
	}
	
	protected void sendEvent(IEvent event)
	{
		EventManager.sendEvent(event, this);
	}
}
