package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class KPMIDSelector extends TextSelector
{

	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		
		if( searchContent.isEmpty() )
		{
			return list;
		}
		
		String id = searchContent.get(0);
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		
		for( TicketEntity t : list )
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
	public void addSearchContent(String content)
	{
		searchContent.clear();
		searchContent.add(content);
	}

	@Override
	public int getSelectorType()
	{
		return IConstants.SELECTOR_KPM_ID;
	}
}
