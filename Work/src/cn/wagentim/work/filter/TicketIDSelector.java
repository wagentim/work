package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketEntity;

public class TicketIDSelector extends AbstractSelector
{
	private String id = StringConstants.EMPTY_STRING;

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

	@Override
	public void setSearchContent(List<String> content)
	{
		// TODO Auto-generated method stub
		
	}

}
