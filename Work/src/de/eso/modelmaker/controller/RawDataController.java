package de.eso.modelmaker.controller;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.tool.DataDBImporter;

public class RawDataController implements IController
{
	private final DataDBImporter importer = new DataDBImporter();
	private int ticketNumber;
	private static final Header[] TABLE_HEADERS = new Header[]{
			new Header("KPM", 60),
			new Header("Short Text", 340)
			};

	@Override
	public Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}

	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		List<Ticket> list = importer.getAllTickets();
		ticketNumber = list.size();
		List<String[]> result = new ArrayList<String[]>();
		
		String number;
		String shortText;
		
		for(Ticket t : list)
		{
			
			if( null == t )
			{
				number = StringConstants.EMPTY_STRING;
				shortText = StringConstants.EMPTY_STRING;
			}
			else
			{
				number = String.valueOf(t.getNumber());
				shortText = t.getShortText();
			}
			
			result.add(new String[]{number, shortText});
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
