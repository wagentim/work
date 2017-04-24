package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class KPMIDSelector extends AbstractSelector
{

	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		String id = searchContent.get(0);
		
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
