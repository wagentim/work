package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.Ticket;

public class CNSelector implements ISelector
{

	@Override
	public List<Ticket> check(List<Ticket> list)
	{
		List<Ticket> result = new ArrayList<Ticket>();
		
		for( Ticket t : list )
		{
			if( null == t )
			{
				continue;
			}
			
			if( t.getMarket().equals("CN") )
			{
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}

}
