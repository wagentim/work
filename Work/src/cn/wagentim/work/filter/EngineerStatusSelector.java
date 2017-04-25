package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class EngineerStatusSelector extends AbstractSelector
{
	
	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		
		for( TicketEntity t : list )
		{
			if( null == t )
			{
				continue;
			}
			
			int es = t.getEnginerringStatus();
			
			for(String i : searchContent)
			{
				if( Integer.valueOf(i) == es )
				{
					result.add(t);
					break;
				}
			}
		}
		
		return result;
	}

	@Override
	public int getSelectorType()
	{
		return IConstants.SELECTOR_ENGINEER_STATUS;
	}


}
