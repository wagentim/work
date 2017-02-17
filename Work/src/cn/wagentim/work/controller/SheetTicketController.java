package cn.wagentim.work.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.SheetTicket;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.service.QLoggerService;

public class SheetTicketController extends DefaultController
{
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(SheetTicketController.class.getSimpleName())));
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
	protected String dbName = StringConstants.EMPTY_STRING;
	protected Map<Integer, SheetTicket> mapComments = null;
	
	public SheetTicketController(final String dbName)
	{
		super();
		this.dbName = dbName;
	}
	
	@Override
	public Header[] getColumnHeaders()
	{
		int oldHeadersLenght = TABLE_HEADERS.length;
		
		Header[] headers = new Header[oldHeadersLenght + 3];
		System.arraycopy(TABLE_HEADERS, 0, headers, 0, oldHeadersLenght);
		
		headers[oldHeadersLenght] = new Header("Priority", 50);
		headers[oldHeadersLenght + 1] = new Header("Next Action", 80);
		headers[oldHeadersLenght + 2] = new Header("Status", 60);
		
		return headers;
	}
	
	protected void getTicketListFromDB()
	{
		List<SheetTicket> tComments = (List<SheetTicket>) importer.getAllRecord(dbName, SheetTicket.class.getSimpleName(), SheetTicket.class);
		mapComments = new HashMap<Integer, SheetTicket>();
		
		changeToMap(tComments, mapComments);
		
		ticketList.clear();
		
		for(SheetTicket st : tComments)
		{
			Ticket ticket = importer.getTicket(st.getKpmID());
			if(null == ticket)
			{
				ticket = new Ticket();
				ticket.setNumber(st.getKpmID());
			}
			ticketList.add(ticket);
		}
	}
	
	private void changeToMap(List<SheetTicket> tComments, Map<Integer, SheetTicket> mapComments2)
	{
		for(SheetTicket st : tComments)
		{
			mapComments2.put(st.getKpmID(), st);
		}
	}

	protected String[] handleTableRowContent(Ticket t, int columns)
	{
		String[] result = super.handleTableRowContent(t, columns);
		
		int startIndex = super.getColumnHeaders().length;
		
		SheetTicket st = mapComments.get(t.getNumber());
		
		if( null == t ||  null == st )
		{
			result[startIndex] = StringConstants.EMPTY_STRING;
			result[startIndex+1] = StringConstants.EMPTY_STRING;
			result[startIndex+2] = StringConstants.EMPTY_STRING;
		}
		else
		{
			result[startIndex] = String.valueOf(st.getPriority());
			result[startIndex+1] = st.getNextStep();
			result[startIndex+2] = st.getStatus();
		}
		
		return result;
	}
	
	public List<String[]> getComments(int selectedTicketNumber)
	{
		List<String[]> result = new ArrayList<String[]>();

		List<SheetTicket> comments = importer.getComments(selectedTicketNumber);
		
		if( !comments.isEmpty() )
		{
			for(SheetTicket tc : comments)
			{
				if( null != tc )
				{
//					result.add(new String[]{sdf.format(new Date(tc.getTime())), tc.getComment()});
				}
			}
		}
		
		return result;
	}


	public void setSheet(String name)
	{
		this.dbName = name;
	}
}
