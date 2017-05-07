package cn.wagentim.work.core;

import de.wagentim.qlogger.channel.AbstractLogChannel;
import de.wagentim.qlogger.console.Console;

public class WorkProjectChannel extends AbstractLogChannel
{

	public WorkProjectChannel(String name)
	{
		super(name);
	}

	@Override
	public Console getConsole()
	{
		return null;
	}
	
	

}
