package cn.wagentim.work.sort;

import java.util.Comparator;

import cn.wagentim.entities.work.TicketEntity;

public class MarketTicketComparator implements Comparator<TicketEntity>
{

	private boolean up = false;
	
	public MarketTicketComparator(boolean up)
	{
		this.up = up;
	}
	@Override
	public int compare(TicketEntity o1, TicketEntity o2)
	{
		if(up) return o2.getMarket().compareTo(o1.getMarket());
		else return o1.getMarket().compareTo(o2.getMarket());
	}

}
