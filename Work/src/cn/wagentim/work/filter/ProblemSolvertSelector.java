package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class ProblemSolvertSelector extends TextSelector
{
	
	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		if( searchContent.isEmpty() )
		{
			return list;
		}
		
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		String s = searchContent.get(0);
		
		for( TicketEntity t : list )
		{
			if( null == t )
			{
				continue;
			}
			
			String stext = t.getResponsibleProblemSolverUser();
			
			if( stext.toLowerCase().contains(s.toLowerCase()))
			{
				result.add(t);
			}
		}
		
		return result;
	}

	@Override
	public int getSelectorType()
	{
		return IConstants.SELECTOR_PROBLEM_SOLVER;
	}
}
