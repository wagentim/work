//package cn.wagentim.work.filter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;
//
//import cn.wagentim.basicutils.StringConstants;
//import cn.wagentim.entities.work.TicketEntity;
//
//public class EngineerStatusSelector extends AbstractSelector
//{
//	private List<Integer> status = new ArrayList<Integer>();
//	
//	@Override
//	public List<TicketEntity> check(List<TicketEntity> list)
//	{
//		List<TicketEntity> result = new ArrayList<TicketEntity>();
//		
//		for( TicketEntity t : list )
//		{
//			if( null == t )
//			{
//				continue;
//			}
//			
//			for(Integer i : status)
//			{
//				if( i == t.getEnginerringStatus() )
//				{
//					result.add(t);
//					break;
//				}
//			}
//		}
//		
//		return result;
//	}
//
//	@Override
//	public void setSearchContent(List<String> content)
//	{
//		
//	}
//
//	@Override
//	public void addSearchContent(String content)
//	{
//		status.clear();
//		StringTokenizer st = new StringTokenizer(content, StringConstants.COMMA);
//		
//		while(st.hasMoreTokens())
//		{
//			status.add(Integer.valueOf(st.nextToken().trim()));
//		}
//	}
//
//	@Override
//	public List<String> getSearchContent()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
