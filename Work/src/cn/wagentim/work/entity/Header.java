package cn.wagentim.work.entity;

import cn.wagentim.basicutils.StringConstants;

public class Header
{
	private String name = StringConstants.EMPTY_STRING;
	private int width = 50;
	
	public Header(String name, int width)
	{
		setName(name);
		setWidth(width);
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	
}
