package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.MustFix;

public class MustFixDataDecorator implements IDecorator
{

	@Override
	public List<?> priority(List<?> list)
	{
		List<MustFix> result = new ArrayList<MustFix>();
		
		if( !list.isEmpty() )
		{
			// Highest Priority will be the first one
			
			for(int i = 0; i < list.size(); i++)
			{
				MustFix mf = (MustFix)list.get(i);
				
				if( null != mf )
				{
					int pri = mf.getPriority();
					
					if( 1 == pri )
					{
						result.add(0, mf);
					}
					else
					{
						result.add(mf);
					}
				}
			}
		}
		
		return result;
	}

}
