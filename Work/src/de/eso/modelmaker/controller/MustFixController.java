package de.eso.modelmaker.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.work.MustFix;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.entities.work.TicketComment;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.IDecorator;
import cn.wagentim.work.filter.MustFixDataDecorator;
import cn.wagentim.work.tool.DataDBImporter;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class MustFixController implements IController
{
	private final Map<Integer, Ticket> mustFixTicket;
	private List<MustFix> mustFixes;
	private List<String[]> tableData;
	private final DataDBImporter importer;
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(MustFixController.class.getSimpleName())));
	private IDecorator decorator;
	
	private static final Header[] TABLE_HEADERS = new Header[]{
			new Header("KPM", 60),
			new Header("Market", 60),
			new Header("Short Text", 340),
			new Header("Supplier", 100),
			new Header("Priority", 60),
			new Header("Next Step", 100)
			};
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
	
	public MustFixController()
	{
		importer = new DataDBImporter();
		mustFixTicket = new HashMap<Integer, Ticket>();
		tableData = new ArrayList<String[]>();
		decorator = new MustFixDataDecorator();
	}
	
	private boolean isMustFixesOK()
	{
		if( mustFixes.isEmpty() )
		{
			logger.log(Log.LEVEL_ERROR, "The Must Fix List is null or empty!");
			return false;
		}
		
		return true;
	}
	
	private void clearTableContents()
	{
		// clear original data
		mustFixTicket.clear();
	}
	
	/**
	 * Load according {@link Ticket} from the DB, which is 1 to 1 relation to the {@link MustFix}
	 */
	@SuppressWarnings("unchecked")
	private void loadTicketFromDB()
	{
		mustFixes = (List<MustFix>) decorator.priority(importer.getAllMustFixList());
		
		MustFix mf = null;
		
		for(int i = 0; i < mustFixes.size(); i++)
		{
			mf = mustFixes.get(i);
			
			if( null == mf )
			{
				logger.log(Log.LEVEL_WARN, "The %1 record from Must Fix List is NULL!", String.valueOf(i));
				continue;
			}
			
			int id = mf.getNumber();
			
			Ticket ticket = importer.getTicket(id);
			
			if( null == ticket )
			{
				logger.log(Log.LEVEL_ERROR, "Cannot find the Ticket from the DB with the ID: %1", String.valueOf(id));
				continue;
			}
			
			mustFixTicket.put(id, ticket);
		}
	}

	@Override
	public final Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}

	@Override
	public final List<String[]> getTableContents(boolean fromDB)
	{
		clearTableContents();
		loadTicketFromDB();
		return updateTableData();
	}
	
	private final List<String[]> updateTableData()
	{
		tableData.clear();
		
		
		for(MustFix mf : mustFixes)
		{
			if( null != mf )
			{
				int number = mf.getNumber();
				Ticket ticket = mustFixTicket.get(number);
				String shortText = StringConstants.EMPTY_STRING;
				String supplier = StringConstants.EMPTY_STRING;
				
				if( null != ticket )
				{
					shortText = ticket.getShortText();
					supplier = ticket.getSupplier();
				}
				
				tableData.add(new String[]{String.valueOf(number), StringConstants.EMPTY_STRING, shortText, supplier, String.valueOf(mf.getPriority()), mf.getNext() });
			}
		}
		
		return tableData;
	}

	@Override
	public Ticket getSelectedTicket(int selectedTicketNumber)
	{
		return mustFixTicket.get(selectedTicketNumber);
	}

	@Override
	public String getTotalDisplayedTicketNumber()
	{
		return String.valueOf(mustFixes.size());
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
					result.add(new String[]{sdf.format(new Date(tc.getTime())), tc.getContent()});
				}
			}
		}
		
		return result;
	}
}
