package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.importer.ImportTickets;

public class SvenSelector extends AbstractSelector
{
	
	private final ImportTickets importer = new ImportTickets();

	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		List<Integer> svenTickets = getSvenTickets();
		
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		
		for( TicketEntity t : list )
		{
			if( null == t )
			{
				continue;
			}
			
			if( !(svenTickets.contains(t.getKPMID())) )
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
		List<TicketEntity> tickets = (List<TicketEntity>) importer.getAllRecord(IConstants.DB_SVEN, "TicketEntity", TicketEntity.class);
		
		for(TicketEntity t : tickets)
		{
			result.add(t.getKPMID());
		}
		
		return result;
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}

}
