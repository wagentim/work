package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.Ticket;

public class RatingSelector implements ISelector
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
			
			if( t.getRating().equals("A") )
			{
				result.add(0, t);
			}
			else if( t.getRating().equals("B") )
			{
				result.add(t);
			}
		}
		
		return result;
	}

}
