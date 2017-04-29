package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class SupplierStatusSelector extends AbstractSelector
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
			
			String stext = t.getlStatus();
			
			for(String s : searchContent)
			{
				if( IConstants.MENU_ITEM_SUPPLIER_STATUS_EMPTY.equals(s))
				{
					s = StringConstants.EMPTY_STRING;
				}
				
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
		return IConstants.SELECTOR_SUPPLIER_STATUS;
	}
}
