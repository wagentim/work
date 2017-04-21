package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketEntity;

public class SupplierSelector extends AbstractSelector
{
	
	List<String> keywords = new ArrayList<String>();

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
			
			for(String s : keywords)
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
	public void setSearchContent(String content)
	{
		keywords.clear();
		StringTokenizer st = new StringTokenizer(content, StringConstants.COMMA);
		
		while(st.hasMoreTokens())
		{
			keywords.add(st.nextToken().trim());
		}
	}

	@Override
	public void setSearchContent(List<String> content)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getSearchContent()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
