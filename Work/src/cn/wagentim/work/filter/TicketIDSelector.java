package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.Ticket;

public class TicketIDSelector implements ISelector
{
	private String id = StringConstants.EMPTY_STRING;

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
			
			if( String.valueOf(t.getId()).startsWith(id) )
			{
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public void setSearchContent(String content)
	{
		this.id = content;
	}

}
