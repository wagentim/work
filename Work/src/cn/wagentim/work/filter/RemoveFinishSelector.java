package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;

public class RemoveFinishSelector extends AbstractSelector
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
			
			if( engineerStatus == 5 || engineerStatus == 6 || engineerStatus == 4 )
			{
				continue;
			}
			else
			{
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}

}
