package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.work.CommentEntity;
import cn.wagentim.entities.work.SheetTicketEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.sort.KPMTicketComparator;
import cn.wagentim.work.sort.MarketTicketComparator;
import cn.wagentim.work.sort.RatingTicketComparator;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class SheetTicketController extends TicketController
{
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(SheetTicketController.class.getSimpleName())));
	protected String sheetName = StringConstants.EMPTY_STRING;
	protected Map<Integer, List<CommentEntity>> commentsMap = null;
	private Map<Integer, SheetTicketEntity> sheetTicketsMap = null;
	private Iterator<Integer> ticketIterator = null;
	
	
	public SheetTicketController(final String sheetName)
	{
		super();
		this.sheetName = sheetName;
		reset();
	}
	
	@Override
	public Header[] getColumnHeaders()
	{
		int oldHeadersLenght = TABLE_HEADERS.length;
		
		Header[] headers = new Header[oldHeadersLenght + 4];
		System.arraycopy(TABLE_HEADERS, 0, headers, 0, oldHeadersLenght);
		
		headers[oldHeadersLenght] = new Header("Comment", 150);
		headers[oldHeadersLenght + 1] = new Header("Priority", 50);
		headers[oldHeadersLenght + 2] = new Header("Next Action", 80);
		headers[oldHeadersLenght + 3] = new Header("Status", 60);
		
		return headers;
	}
	
	@SuppressWarnings("unchecked")
	private void loadDataFromDB()
	{
		reset();
		
		List<SheetTicketEntity> sheetTicketsList = (List<SheetTicketEntity>) importer.getAllRecord(sheetName, SheetTicketEntity.class.getSimpleName(), SheetTicketEntity.class);
		
		if( sheetTicketsList.isEmpty() )
		{
			logger.log(Log.LEVEL_INFO, "Null or Empty sheet tickets with Sheet name: %1", sheetName);
			return;
		}
		
		convertToMap(sheetTicketsList);
		
		loadTickets();
		
		if(!loadCommentsFromDB())
		{
			logger.log(Log.LEVEL_ERROR, "No KPM IDs for loading the comments");
			resetCommentsMap();
			return;
		}
	}
	
	private void loadTickets()
	{
		List<TicketEntity> tickets = importer.getAllTickets();
		
		Set<Integer> sheetTickets = sheetTicketsMap.keySet();
		
		for(TicketEntity te : tickets)
		{
			if( sheetTickets.contains(te.getKPMID()))
			{
				ticketMap.put(te.getKPMID(), te);
			}
		}
	}

	public List<String[]> getTableContents(boolean fromDB)
	{
		if( fromDB )
		{
			loadDataFromDB();
			ticketIterator = sheetTicketsMap.keySet().iterator();
		}
		
		List<String[]> result = new ArrayList<String[]>();
		
		while(ticketIterator.hasNext())
		{
			int kpmID = ticketIterator.next();
			TicketEntity ticket = (TicketEntity) ticketMap.get(kpmID);
			List<CommentEntity> comments = commentsMap.get(kpmID);
			result.add(genTableRowContent(sheetTicketsMap.get(kpmID), ticket, comments));
		}
		
		ticketNumber = result.size();
		
		return result;
	}
	
	private boolean loadCommentsFromDB()
	{
		Set<Integer> kpmIDs = sheetTicketsMap.keySet();
		
		if( kpmIDs.isEmpty() )
		{
			return false;
		}
		
		for(Integer kpmID : kpmIDs)
		{
			List<CommentEntity> ticketComments = importer.getComments(kpmID);
			
			commentsMap.put(kpmID, ticketComments);
		}
		
		return true;
	}

	private void convertToMap(List<SheetTicketEntity> sheetList)
	{
		for(SheetTicketEntity st : sheetList)
		{
			sheetTicketsMap.put(st.getKpmID(), st);
		}
	}	

	protected String[] genTableRowContent(SheetTicketEntity sheetTicket, TicketEntity ticket, List<CommentEntity> comments)
	{
		String[] result = super.genTableRowContent(ticket);
		
		int startIndex = super.TABLE_HEADERS.length;
		
		CommentEntity ce =  getLatestComment(comments);
		result[startIndex] = ( (ce == null) ? StringConstants.EMPTY_STRING : ce.getComment());
		result[startIndex + 1] = String.valueOf(sheetTicket.getPriority());
		result[startIndex + 2] = sheetTicket.getNextStep();
		result[startIndex + 3] = sheetTicket.getStatus();
		
		return result;
	}
	
	private CommentEntity getLatestComment(List<CommentEntity> comments)
	{
		CommentEntity result = null;
		
		for(CommentEntity ce : comments)
		{
			if( (null == result) || (ce.getTime() > result.getTime()) )
			{
				result = ce;
			}
		}
		
		return result;
	}
	
	public List<String[]> getCommentsForCommentViewer(int selectedTicketNumber)
	{
		List<String[]> result = new ArrayList<String[]>();
		
		List<CommentEntity> listComments = commentsMap.get(selectedTicketNumber);
		Collections.sort(listComments, new Comparator<CommentEntity>()
		{

			@Override
			public int compare(CommentEntity o1, CommentEntity o2)
			{
				return o1.compareTo(o2);
			}
		});
		
		if( !listComments.isEmpty() )
		{
			for(CommentEntity ce : listComments)
			{
				if( null != ce )
				{
					result.add(new String[]{IConstants.SIMPLE_DATE_FORMAT.format(new Date(ce.getTime())), ce.getAuthor(), ce.getComment()});
				}
			}
		}
		
		return result;
	}


	public void setSheet(String sheetName)
	{
		if( Validator.isNullOrEmpty(sheetName) )
		{
			logger.log(Log.LEVEL_ERROR, "The Sheet Name is Null or Empty");
			return;
		}
		
		this.sheetName = sheetName;
		
		reset();
	}
	
	private void reset()
	{
		resetTicketList();
		
		resetCommentsMap();
		
		resetSheetTicketMap();
	}

	private void resetSheetTicketMap()
	{
		if( null == sheetTicketsMap )
		{
			sheetTicketsMap = new HashMap<Integer, SheetTicketEntity>();
		}
		else
		{
			sheetTicketsMap.clear();
		}
	}
	
	private void resetCommentsMap()
	{
		if( null == commentsMap )
		{
			commentsMap = new HashMap<Integer, List<CommentEntity>>();
		}
		else
		{
			commentsMap.clear();
		}
	}
	
	@Override
	public void columnSelected(String columnName)
	{
		List<TicketEntity> ticketList = createTicketList();
		
		if( IConstants.STRING_HEADER_KPM.equals(columnName) )
		{
			kpmsort = !kpmsort;
			Collections.sort(ticketList, new KPMTicketComparator(kpmsort));
		}
		else if( IConstants.STRING_HEADER_RATING.equals(columnName) )
		{
			ratingsort = !ratingsort;
			Collections.sort(ticketList, new RatingTicketComparator(ratingsort));
		}
		else if( IConstants.STRING_HEADER_MARKET.equals(columnName) )
		{
			marketsort = !marketsort;
			Collections.sort(ticketList, new MarketTicketComparator(marketsort));
		}

		List<Integer> idList = new ArrayList<Integer>();
		
		for(TicketEntity te : ticketList)
		{
			idList.add(te.getKPMID());
		}
		
		ticketIterator = idList.iterator();
		
	}

	private List<TicketEntity> createTicketList()
	{
		Set<Integer> kpmIDs = sheetTicketsMap.keySet();
		
		List<TicketEntity> result = new ArrayList<TicketEntity>();
		
		for(Integer kpmID : kpmIDs)
		{
			TicketEntity ticket = (TicketEntity) ticketMap.get(kpmID);
			
			if( null == ticket )
			{
				ticket = new TicketEntity();
				ticket.setNumber(kpmID);
			}
			
			result.add(ticket);
			
		}
		
		return result;
	}
	
	@Override
	public void decorateOutput(List<String> headers, List<List<String>> currentTableContent)
	{
		super.decorateOutput(headers, currentTableContent);
	}
	
	public void addComment(int kpmID, String[] comment)
	{
		CommentEntity ce = new CommentEntity();
		ce.setKpmID(kpmID);
		ce.setTime(Long.valueOf(comment[0]));
		ce.setAuthor(comment[1]);
		ce.setComment(comment[2]);
		
		importer.updateEntity(ce, IConstants.DB_TICKET_COMMENT);
		loadCommentsFromDB();
	}

	public void removeTicket(List<Integer> selectedTicketNumbers)
	{
		if(!selectedTicketNumbers.isEmpty())
		{
			for(Integer id : selectedTicketNumbers )
			{
				importer.deleteEntity(sheetName, "SheetTicketEntity", "kpmID", id, SheetTicketEntity.class);
			}
		}
	}
}
