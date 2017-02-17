package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.Sheet;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.entities.work.SheetTicket;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.importer.DataDBImporter;

public class DefaultController extends AbstractController
{
	protected final DataDBImporter importer = new DataDBImporter();
	protected int ticketNumber;
	protected static final Header[] TABLE_HEADERS = new Header[]{
			new Header("KPM", 60),
			new Header("Short Text", 340),
			new Header("Rating", 60),
			new Header("Market", 60),
			new Header("Problem Solver", 200)
			};
	
	protected List<Ticket> ticketList;
	
	protected List<ISelector> selectors = new ArrayList<ISelector>();
	
	public DefaultController()
	{
		ticketList = new ArrayList<Ticket>();
	}
	
	@Override
	public Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}
	
	private void Sven()
	{
//		selectors.add(new RemoveFinishSelector());
//		selectors.add(new SvenSelector());
//		selectors.add(new EmptyMarketSelector());
//		selectors.add(new ShortTextSelector());
//		selectors.add(new RatingSelector());
////		selectors.add(new CNSelector());
//		selectors.add(new SupplierSelector());
	}
	
	@Override
	public void addSelectors(ISelector selector)
	{
		selectors.add(selector);
	}
	
	@Override
	public void clearSelectors()
	{
		selectors.clear();
	}
	
	protected void getTicketListFromDB()
	{
		ticketList = importer.getAllTickets();
	}

	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		if(fromDB)
		{
			getTicketListFromDB();
		}
		
		List<Ticket> tmp = ticketList;
		
		tmp = filter(tmp);
		
		ticketNumber = tmp.size();
		List<String[]> result = new ArrayList<String[]>();
		
		for(Ticket t : tmp)
		{
			String[] row = handleTableRowContent(t, getColumnHeaders().length);
			result.add(row);
		}
		return result;
	}

	protected String[] handleTableRowContent(IEntity entity, int columns)
	{
		Ticket t = (Ticket)entity;
		
		String[] result = new String[columns];
		
		if( null == t )
		{
			result[0] = StringConstants.EMPTY_STRING;
			result[1] = StringConstants.EMPTY_STRING;
			result[2] = StringConstants.EMPTY_STRING;
			result[3] = StringConstants.EMPTY_STRING;
			result[4] = StringConstants.EMPTY_STRING;
		}
		else
		{
			result[0] = String.valueOf(t.getNumber());
			result[1] = t.getShortText();
			result[2] = t.getRating();
			result[3] = t.getMarket();
			result[4] = t.getResponsibleProblemSolverUser();
		}
		
		return result;
	}
	
	private List<Ticket> filter(List<Ticket> list)
	{
		List<Ticket> result = list;
		
		for(ISelector selector : selectors )
		{
			result = selector.check(result);
		}
		
		return result;
	}

	@Override
	public Ticket getSelectedTicket(int selectedTicketNumber)
	{
		return importer.getTicket(selectedTicketNumber);
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

	@Override
	public void setSearchContent(String content)
	{
		for(ISelector sel : selectors)
		{
			sel.setSearchContent(content);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sheet> getAllSheets()
	{
		return (List<Sheet>) importer.getAllRecord(IConstants.DB_SHEET, "Sheet", Sheet.class);
	}

	@Override
	public void deleteEntity(String db, String entity, String column, String value, @SuppressWarnings("rawtypes") Class clazz)
	{
		importer.deleteEntity(db, entity, column, value, clazz);
	}

	@Override
	public void addTicketComment(String dbName, int kpmid)
	{
		SheetTicket tc = new SheetTicket();
		tc.setKpmID(kpmid);
		importer.updateEntity(tc, dbName);
	}
}
