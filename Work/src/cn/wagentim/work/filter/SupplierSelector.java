package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class SupplierSelector extends AbstractSelector
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
			
			String stext = t.getSupplier();
			
			for(String s : searchContent)
			{
				if( stext.contains(s))
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
		return IConstants.SELECTOR_SUPPLIER;
	}
}
