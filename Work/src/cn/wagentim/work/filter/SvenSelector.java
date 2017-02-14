package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.tool.DataDBImporter;

public class SvenSelector implements ISelector
{
	
	private final DataDBImporter importer = new DataDBImporter();

	@Override
	public List<Ticket> check(List<Ticket> list)
	{
		List<Integer> svenTickets = getSvenTickets();
		
		List<Ticket> result = new ArrayList<Ticket>();
		
		for( Ticket t : list )
		{
			if( null == t )
			{
				continue;
			}
			
			if( !(svenTickets.contains(t.getNumber())) )
			{
				result.add(t);
			}
		}
		
		return result;
	}

	private List<Integer> getSvenTickets()
	{
		List<Integer> result = new ArrayList<Integer>();
		
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = (List<Ticket>) importer.getAllRecord(IConstants.DB_SVEN, "Ticket", Ticket.class);
		
		for(Ticket t : tickets)
		{
			result.add(t.getNumber());
		}
		
		return result;
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}

}
