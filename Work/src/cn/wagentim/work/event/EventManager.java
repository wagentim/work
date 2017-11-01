package cn.wagentim.work.event;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.wagentim.basicutils.Validator;

public final class EventManager
{
	private static final List<IEventComponent> componentList = new CopyOnWriteArrayList<IEventComponent>();
	
	public static void registComponent(IEventComponent component)
	{
		if( !Validator.isNull(component) && !isContained(component) )
		{
			componentList.add(component);
		}
	}
	
	public static void sendEvent(IEvent event, IEventComponent sender)
	{
		Iterator<IEventComponent> it = componentList.iterator();
		
		while(it.hasNext())
		{
			IEventComponent currComponent = it.next();
			
			if( currComponent != sender )
			{
				currComponent.receiveEvent(event);
			}
		}
	}
	
	public static void clearEventComponents()
	{
		componentList.clear();
	}
	
	public static void removeEventComponent(IEventComponent component)
	{
		componentList.remove(component);
	}
	
	private static boolean isContained(IEventComponent component)
	{
		return componentList.indexOf(component) > 0 ? true : false;
	}
}

