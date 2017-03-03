package cn.wagentim.work.sort;

import java.util.Comparator;

import cn.wagentim.entities.work.TicketEntity;

public class KPMTicketComparator implements Comparator<TicketEntity>
{
	
	private boolean up = false;
	
	public KPMTicketComparator(boolean up)
	{
		this.up = up;
	}

	@Override
	public int compare(TicketEntity o1, TicketEntity o2)
	{
		if(up) return o2.getKPMID() - o1.getKPMID();
		else return o1.getKPMID() - o2.getKPMID();
	}

}
