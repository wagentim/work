package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.SheetEntity;
import cn.wagentim.entities.work.SheetTicketEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.sort.KPMTicketComparator;
import cn.wagentim.work.sort.MarketTicketComparator;
import cn.wagentim.work.sort.RatingTicketComparator;

public class TicketController extends AbstractController
{
	protected int ticketNumber = 0;
	protected static final Header[] TABLE_HEADERS = new Header[]{
			new Header(IConstants.STRING_HEADER_KPM, 60),
			new Header("Short Text", 340),
			new Header(IConstants.STRING_HEADER_RATING, 60),
			new Header(IConstants.STRING_HEADER_MARKET, 60),
			new Header("Problem Solver", 200)
			};
	
	protected SheetManagerController sheetController;
	protected List<TicketEntity> origTicketList;
	protected List<TicketEntity> currTicketList;
	protected Map<Integer, TicketEntity> ticketMap = null;
	
	protected boolean kpmsort = false;
	protected boolean ratingsort = false;
	protected boolean marketsort = false;
	
	public TicketController()
	{
		sheetController = new SheetManagerController();
		resetTicketList();
	}
	
	@Override
	public Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}
	
	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		if(fromDB)
		{
			resetTicketList();
			origTicketList = importer.getAllTickets();
			convertToMap();
		}
		
		List<TicketEntity> tmp = origTicketList;

		if( null == tmp )
		{
			tmp = new ArrayList<TicketEntity>();
		}
		
		tmp = filter(tmp);
		
		ticketNumber = tmp.size();
		List<String[]> result = new ArrayList<String[]>();
		
		for(TicketEntity t : tmp)
		{
			String[] row = genTableRowContent(t);
			result.add(row);
		}
		return result;
	}

	private void convertToMap()
	{
		for(TicketEntity t : origTicketList)
		{
			ticketMap.put(t.getKPMID(), t);
		}
	}

	protected String[] genTableRowContent(TicketEntity entity)
	{
		
		String[] result = new String[getColumnHeaders().length];
		
		if( null == entity )
		{
			result[0] = StringConstants.EMPTY_STRING;
			result[1] = StringConstants.EMPTY_STRING;
			result[2] = StringConstants.EMPTY_STRING;
			result[3] = StringConstants.EMPTY_STRING;
			result[4] = StringConstants.EMPTY_STRING;
		}
		else
		{
			result[0] = String.valueOf(entity.getKPMID());
			result[1] = entity.getShortText();
			result[2] = entity.getRating();
			result[3] = entity.getMarket();
			result[4] = entity.getResponsibleProblemSolverUser();
		}
		
		return result;
	}
	
	protected List<TicketEntity> filter(List<TicketEntity> list)
	{
		List<TicketEntity> result = list;
		
		for(ISelector selector : selMgr.getSelectors() )
		{
			result = selector.check(result);
		}
		
		return result;
	}

	@Override
	public TicketEntity getSelectedTicket(int selectedTicketNumber)
	{
		return ticketMap.get(selectedTicketNumber);
	}

	@Override
	public String getTotalDisplayedTicketNumber()
	{
		return String.valueOf(ticketNumber);
	}

	@Override
	public void updateRecord(IEntity entity)
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SheetEntity> getAllSheets()
	{
		return (List<SheetEntity>) importer.getAllRecord(IConstants.DB_SHEET, "SheetEntity", SheetEntity.class);
	}

	@Override
	public void deleteEntity(String db, String entity, String column, String value, @SuppressWarnings("rawtypes") Class clazz)
	{
		importer.deleteEntity(db, entity, column, value, clazz);
	}

	
	protected void resetTicketList()
	{
		if( null == origTicketList )
		{
			origTicketList = new ArrayList<TicketEntity>();
		}
		else
		{
			origTicketList.clear();
		}
		
		if( null == ticketMap )
		{
			ticketMap = new TreeMap<Integer, TicketEntity>();
		}
		else
		{
			ticketMap.clear();
		}
		
		if( null == currTicketList )
		{
			currTicketList = new ArrayList<TicketEntity>();
		}
		else
		{
			currTicketList.clear();
		}
	}

	@Override
	public void columnSelected(String columnName)
	{
		if( IConstants.STRING_HEADER_KPM.equals(columnName) )
		{
			kpmsort = !kpmsort;
			Collections.sort(origTicketList, new KPMTicketComparator(kpmsort));
		}
		else if( IConstants.STRING_HEADER_RATING.equals(columnName) )
		{
			ratingsort = !ratingsort;
			Collections.sort(origTicketList, new RatingTicketComparator(ratingsort));
		}
		else if( IConstants.STRING_HEADER_MARKET.equals(columnName) )
		{
			marketsort = !marketsort;
			Collections.sort(origTicketList, new MarketTicketComparator(marketsort));
		}
		
	}

	@Override
	public void decorateOutput(List<String> headers, List<List<String>> currentTableContent)
	{
		headers.add("Engineer Status");
		headers.add("Fault Frequency");
		headers.add("Problem Solver");
		
		for(List<String> content : currentTableContent)
		{
			TicketEntity te = ticketMap.get(Integer.valueOf(content.get(0)));
			
			if( null == te )
			{
				content.add(StringConstants.EMPTY_STRING);
				content.add(StringConstants.EMPTY_STRING);
				content.add(StringConstants.EMPTY_STRING);
			}
			else
			{
				content.add(String.valueOf(te.getEnginerringStatus()));
				content.add(te.getFaultFrequency());
				content.add(te.getResponsibleProblemSolverUser());
			}
		}
	}

	@Override
	public void addTicketComment(String dbName, int kpmid)
	{
		SheetTicketEntity tc = new SheetTicketEntity();
		tc.setKpmID(kpmid);
		importer.updateEntity(tc, dbName);
	}

}
