package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketEntity;

public class Clu8P extends AbstractSelector
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
			
			int engineerStatus = t.getEnginerringStatus();
			String swVersion = t.getSw();
			String supplier = t.getSupplier();
			
			if( engineerStatus == 5 || engineerStatus == 6 || engineerStatus == 4 )
			{
				continue;
			}
//			else if(!swVersion.contains("09") )
//			{
//				continue;
//			}
			else if( !(t.getMarket().equals("CN")) )
			{
				continue;
			}
			else if( (!supplier.toLowerCase().contains("esoa")) && (!supplier.toLowerCase().contains(StringConstants.EMPTY_STRING)) )
			{
				continue;
			}
			
			result.add(t);
		}
		
		return result;
	}

	@Override
	public void setSearchContent(List<String> content)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSearchContent(String content)
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
