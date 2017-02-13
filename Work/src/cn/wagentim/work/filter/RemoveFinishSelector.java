package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.Ticket;

public class RemoveFinishSelector implements ISelector
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
			
			int engineerStatus = t.getEnginerringStatus();
			
			if( engineerStatus == 5 || engineerStatus == 6 || engineerStatus == 4 )
			{
				continue;
			}
			else
			{
				result.add(t);
			}
		}
		
		return result;
	}

}
