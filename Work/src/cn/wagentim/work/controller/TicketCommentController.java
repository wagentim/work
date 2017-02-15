package cn.wagentim.work.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.TicketComment;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.service.QLoggerService;

public class TicketCommentController extends DefaultController
{
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(TicketCommentController.class.getSimpleName())));
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
	protected String dbName = StringConstants.EMPTY_STRING;
	protected Map<Integer, List<TicketComment>> tcs;
	
	public TicketCommentController(final String dbName)
	{
		super();
		this.dbName = dbName;
		tcs = new HashMap<Integer, List<TicketComment>>();
	}
	
	protected void getTicketListFromDB()
	{
		List<TicketComment> tComments = (List<TicketComment>) importer.getAllRecord(dbName, TicketComment.class.getSimpleName(), TicketComment.class);
		ticketList.clear();
		
		for(TicketComment tc : tComments)
		{
			if(tcs.containsKey(tc.getNumber()))
			{
				(tcs.get(tc.getNumber())).add(tc);
			}
			else
			{
				List<TicketComment> list = new ArrayList<TicketComment>();
				list.add(tc);
				tcs.put(tc.getNumber(), list);
			}
		}
		
		for(Integer id : tcs.keySet())
		{
			ticketList.add(importer.getTicket(id));
		}
	}
	
	public List<String[]> getComments(int selectedTicketNumber)
	{
		List<String[]> result = new ArrayList<String[]>();

		List<TicketComment> comments = importer.getComments(selectedTicketNumber);
		
		if( !comments.isEmpty() )
		{
			for(TicketComment tc : comments)
			{
				if( null != tc )
				{
					result.add(new String[]{sdf.format(new Date(tc.getTime())), tc.getComment()});
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
