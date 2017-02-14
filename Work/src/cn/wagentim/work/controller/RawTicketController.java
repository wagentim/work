package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.tool.DataDBImporter;

public class RawTicketController extends AbstractController
{
	private final DataDBImporter importer = new DataDBImporter();
	private int ticketNumber;
	private static final Header[] TABLE_HEADERS = new Header[]{
			new Header("KPM", 60),
			new Header("Short Text", 340),
			new Header("Rating", 60),
			new Header("Market", 60),
			new Header("Problem Solver", 200)
			};
	
	private List<Ticket> ticketList;
	
	public RawTicketController()
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

	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		if(fromDB)
		{
			ticketList = importer.getAllTickets();
		}
		
		List<Ticket> tmp = ticketList;
		
		tmp = filter(tmp);
		
		ticketNumber = tmp.size();
		List<String[]> result = new ArrayList<String[]>();
		
		String number;
		String shortText;
		String rating;
		String market;
		String problemSolver;
		
		for(Ticket t : tmp)
		{
			
			if( null == t )
			{
				number = StringConstants.EMPTY_STRING;
				shortText = StringConstants.EMPTY_STRING;
				rating = StringConstants.EMPTY_STRING;
				market = StringConstants.EMPTY_STRING;
				problemSolver = StringConstants.EMPTY_STRING;
			}
			else
			{
				number = String.valueOf(t.getNumber());
				shortText = t.getShortText();
				rating = t.getRating();
				market = t.getMarket();
				problemSolver = t.getResponsibleProblemSolverUser();
			}
			
			result.add(new String[]{number, shortText, rating, market, problemSolver});
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

}
