package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.Ticket;

public class EmptyMarketSelector implements ISelector
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
			
			if( !t.getMarket().equals(StringConstants.EMPTY_STRING))
			{
				result.add(t);
			}
		}
		
		return result;
	}

}
