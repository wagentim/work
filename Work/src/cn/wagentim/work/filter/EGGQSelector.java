package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;

public class EGGQSelector extends AbstractSelector
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
			
			if( t.getEnginerringStatus() == 2 && (t.getShortText().toLowerCase().contains("[eg]") || t.getShortText().toLowerCase().contains("[gq]") || t.getShortText().toLowerCase().contains("[i/gq]")) && !t.getShortText().toLowerCase().contains("[appdb]") && !t.getShortText().toLowerCase().contains("[db]")
					&& !t.getSupplier().toLowerCase().contains("fp"))
			{
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public void setSearchContent(List<String> content)
	{
		
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}
}
