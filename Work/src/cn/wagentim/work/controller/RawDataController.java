package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.EmptyMarketSelector;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.filter.RatingSelector;
import cn.wagentim.work.filter.RemoveFinishSelector;
import cn.wagentim.work.filter.ShortTextSelector;
import cn.wagentim.work.filter.SupplierSelector;
import cn.wagentim.work.filter.SvenSelector;
import cn.wagentim.work.tool.DataDBImporter;

public class RawDataController implements IController
{
	private final DataDBImporter importer = new DataDBImporter();
	private int ticketNumber;
	private static final Header[] TABLE_HEADERS = new Header[]{
			new Header("KPM", 60),
			new Header("Short Text", 340),
			new Header("Rating", 60),
			new Header("Market", 100)
			};
	
	private List<ISelector> selectors = new ArrayList<ISelector>(); 
	
	@Override
	public Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}
	
	private void addSelectors()
	{
		selectors.add(new RemoveFinishSelector());
		selectors.add(new SvenSelector());
		selectors.add(new EmptyMarketSelector());
		selectors.add(new ShortTextSelector());
		selectors.add(new RatingSelector());
//		selectors.add(new CNSelector());
		selectors.add(new SupplierSelector());
	}

	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		addSelectors();
		List<Ticket> list = importer.getAllTickets();
		
		list = filter(list);
		
		ticketNumber = list.size();
		List<String[]> result = new ArrayList<String[]>();
		
		String number;
		String shortText;
		String rating;
		String market;
		
		for(Ticket t : list)
		{
			
			if( null == t )
			{
				number = StringConstants.EMPTY_STRING;
				shortText = StringConstants.EMPTY_STRING;
				rating = StringConstants.EMPTY_STRING;
				market = StringConstants.EMPTY_STRING;
			}
			else
			{
				number = String.valueOf(t.getNumber());
				shortText = t.getShortText();
				rating = t.getRating();
				market = t.getMarket();
			}
			
			result.add(new String[]{number, shortText, rating, market});
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

}
