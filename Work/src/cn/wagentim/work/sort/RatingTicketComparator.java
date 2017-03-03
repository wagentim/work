package cn.wagentim.work.sort;

import java.util.Comparator;

import cn.wagentim.entities.work.TicketEntity;

public class RatingTicketComparator implements Comparator<TicketEntity>
{

	private boolean up = false;
	
	public RatingTicketComparator(boolean up)
	{
		this.up = up;
	}
	@Override
	public int compare(TicketEntity o1, TicketEntity o2)
	{
		if(up) return o2.getRating().compareTo(o1.getRating());
		else return o1.getRating().compareTo(o2.getRating());
	}

}
