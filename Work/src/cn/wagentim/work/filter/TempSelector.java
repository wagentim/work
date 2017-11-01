package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class TempSelector extends AbstractSelector
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
			
			String stext = t.getAnalysis().trim().toLowerCase();
			
			if (stext.startsWith("[retest]") || stext.startsWith("[additional"))
			{
				result.add(t);
			}
		}
		return result;
	}

	@Override
	public int getSelectorType()
	{
		return IConstants.SELECTOR_SUPPLIER;
	}
	
	@Override
	public void addSearchContent(String content)
	{
	}
}
