package cn.wagentim.work.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.MustFix;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.entities.work.TicketComment;
import cn.wagentim.managers.IPersistanceManager;
import cn.wagentim.managers.ObjectDBManager;
import cn.wagentim.work.config.IConfigure;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.config.MustFixTicketConfigure;
import cn.wagentim.work.excel.ExcelReader;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

/**
 * Help class for loading original Excel file data into the according DB
 * 
 * @author ehuabi0
 *
 */
public class DataDBImporter
{

	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(DataDBImporter.class.getSimpleName())));
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
	
	public static void main(String[] args)
	{
		DataDBImporter importer = new DataDBImporter();
		
		MustFixTicketConfigure configure = new MustFixTicketConfigure();
		
		importer.readDataToDB(configure);
	}
	
	/*
	 * Update the KPM Raw Data into the internal DB for the further analysis and
	 * update
	 */
	public void readDataToDB(IConfigure configure)
	{
		logger.log(Log.LEVEL_INFO, "Start to reading raw data: %1", configure.getSourceFilePath());
		
		if( Validator.isNull(configure) )
		{
			logger.log(Log.LEVEL_ERROR, "The configure file is NULL");
			return;
		}
		
		Sheet sheet = loadingSheet(configure);
		
		if( null == sheet )
		{
			logger.log(Log.LEVEL_ERROR, "Sheet is NULL");
			return;
		}

		List<IEntity> list = new ArrayList<IEntity>();
		Iterator rowIter = sheet.rowIterator();
		
		int currentRow = skipRows(rowIter, configure);
		
		while (rowIter.hasNext())
		{
			Row myRow = (Row) rowIter.next();
			currentRow++;
			
			if( null == myRow )
			{
				logger.log(Log.LEVEL_ERROR, "Row %1 is NULL", String.valueOf(currentRow));
				continue;
			}
			
			IEntity result = null;
			
			if( null != ( result = (assignValues(configure, myRow))) )
			{
				list.add(result);
			}
		}
		
		updateDBEntities(list, configure);
		
		logger.log(Log.LEVEL_INFO, "%1 records has been saved to DB", String.valueOf(list.size()));
	}
	
	private IEntity assignValues(IConfigure configure, Row myRow)
	{
		String dbName = configure.getDBName();
		
		if( IConstants.DB_ROW_DATA.equals(dbName) )
		{
			return assignTicketValues(myRow);
		}
		else if( IConstants.DB_MUAS_FIX.equals(dbName) )
		{
			return assignMustFix(myRow);
		}
		else
		{
			return null;
		}
	}
	
	private MustFix assignMustFix(Row myRow)
	{
		MustFix mustFix = new MustFix();
		
		Iterator cellIter = myRow.cellIterator();

		while (cellIter.hasNext())
		{

			int number = 0;
			
			Cell myCell = (Cell) cellIter.next();
			
			int columnIndex = myCell.getColumnIndex();
			String columnName = CellReference.convertNumToColString(columnIndex);
			
			if(columnName.equals("A"))
			{
				number = (int)myCell.getNumericCellValue();
				
				if( number <= 0 )
				{
					logger.log(Log.LEVEL_INFO, "KPM Number is incorrect with the value: %1", String.valueOf(number));
					break;
				}
				
				mustFix.setNumber(number);
			}
			else if(columnName.equals("E"))
			{
				// parser the comments to the DB
//				handleComment(mustFix.getNumber(), getCellValueAsString(myCell));
			}
			else if(columnName.equals("F"))
			{
				mustFix.setPriority((int)myCell.getNumericCellValue()); 
			}
			else if(columnName.equals("G"))
			{
				mustFix.setNext(getCellValueAsString(myCell));
			}
		}
		
		return mustFix;
	}
	
	private void handleComment(int number, String cellValueAsString)
	{
		if( number <= 0 )
		{
			logger.log( Log.LEVEL_ERROR, "Error KPM Number: %1", String.valueOf(number) );
			return;
		}
		
		List<IEntity> comments = parserComments(number, cellValueAsString);
		
		if( !comments.isEmpty() )
		{
			IPersistanceManager manager = new ObjectDBManager();
			manager.connectDB(StringConstants.EMPTY_STRING, 0, "ticketcomments.odb");
			manager.addOrUpdateEntityList(comments);
		}
		
	}

	private List<IEntity> parserComments(int number, String cellValueAsString)
	{
		List<IEntity> result = new ArrayList<IEntity>();
		TicketComment tc = null;
		
		if( !cellValueAsString.isEmpty() )
		{
			StringBuffer time = new StringBuffer();
			StringBuffer comment = new StringBuffer();
			boolean isTime = false;
			
			for(int i = 0; i < cellValueAsString.length(); i++)
			{
				char c = cellValueAsString.charAt(i);
				
				switch (c)
				{
					case '[':
						if( null != (tc = parserComment(number, time, comment))) result.add(tc) ;
						isTime = true;
						comment.delete(0, comment.length());
						time.delete(0, time.length());
						break;
					case ']':
						isTime = false;
						break;
					default:
						if(isTime)
						{
							time.append(c);
						}
						else
						{
							comment.append(c);
						}
						
				}
				
				if( i == cellValueAsString.length() - 1 )
				{
					if( null != (tc = parserComment(number, time, comment))) result.add(tc) ;
				}
			}
		}
		
		return result;
	}

	private TicketComment parserComment(int number, StringBuffer time, StringBuffer comment)
	{
		if( time.length() <=0 && comment.length() <= 0 )
		{
			return null;
		}
		
		TicketComment tc = new TicketComment();
		tc.setTime(parserTime(time));
		tc.setNumber(number);
		tc.setContent(comment.toString());
		return tc;
	}

	private long parserTime(StringBuffer time)
	{
		Date date = null;
		
		try
		{
			date = sdf.parse(time.toString());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		if( null == date )
		{
			return 0;
		}
		
		return date.getTime();
	}

	private Ticket assignTicketValues(Row myRow)
	{
		Ticket ticket = new Ticket();
		Iterator cellIter = myRow.cellIterator();

		while (cellIter.hasNext())
		{

			Cell myCell = (Cell) cellIter.next();
			
			int columnIndex = myCell.getColumnIndex();
			String columnName = CellReference.convertNumToColString(columnIndex);
			
			if(columnName.equals("A"))
			{
				// To Nothing
			}
			else if(columnName.equals("B"))
			{
				int number = (int)myCell.getNumericCellValue();
				
				if( number <= 0 )
				{
					System.out.println("KPM Number is incorrect");
					break;
				}
				
				ticket.setNumber(number);
			}
			else if(columnName.equals("C"))
			{
				ticket.setChangeTS(getCellValueAsString(myCell));
			}
			else if(columnName.equals("I"))
			{
				ticket.setEproject(getCellValueAsString(myCell));
			}
			else if(columnName.equals("J"))
			{
				ticket.setShortText(getCellValueAsString(myCell));
			}
			else if(columnName.equals("K"))
			{
				ticket.setProblemDescription(getCellValueAsString(myCell));
			}
			else if(columnName.equals("L"))
			{
				ticket.setAnalysis(getCellValueAsString(myCell));
			}
			else if(columnName.equals("N"))
			{
				ticket.setFunctionality(getCellValueAsString(myCell));
			}
			else if(columnName.equals("R"))
			{
				ticket.setFaultFrequency(getCellValueAsString(myCell));
			}
			else if(columnName.equals("S"))
			{
				ticket.setProject(getCellValueAsString(myCell));
			}
			else if(columnName.equals("Y"))
			{
				ticket.setSw(getCellValueAsString(myCell));
			}
			else if(columnName.equals("Z"))
			{
				ticket.setDeviceType(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AK"))
			{
				ticket.setRating(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AM"))
			{
				ticket.setStatus((int)myCell.getNumericCellValue());
			}
			else if(columnName.equals("AN"))
			{
				ticket.setEnginerringStatus((int)myCell.getNumericCellValue());
			}
			else if(columnName.equals("AO"))
			{
				ticket.setCreator(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AP"))
			{
				ticket.setTypistUser(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AQ"))
			{
				ticket.setCoordinator(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AR"))
			{
				ticket.setCoordinatorUser(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AS"))
			{
				ticket.setSpclstCo(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AT"))
			{
				ticket.setSpecialistCoordinatorUser(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AU"))
			{
				ticket.setResponsibleProblemSolver(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AV"))
			{
				ticket.setResponsibleProblemSolverUser(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AW"))
			{
				ticket.setImplementationDate(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AZ"))
			{
				ticket.setChangeTSSupplier(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BA"))
			{
				ticket.setSupplier(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BC"))
			{
				ticket.setlStatus(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BG"))
			{
				ticket.setSupplierResponse(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BH"))
			{
				ticket.setSupplierInfo(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BL"))
			{
				ticket.setVerificationStatus(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BM"))
			{
				ticket.setVerificationSWVersion(getCellValueAsString(myCell));
			}
			else if(columnName.equals("BO"))
			{
				ticket.setVerification(getCellValueAsString(myCell));
			}
		}
		
		return ticket;
	}

	private int skipRows(Iterator rowIter, IConfigure configure)
	{
		int skipRows = configure.getFirstSkippedRows();
		
		if( skipRows <= 0 )
		{
			return 0;
		}
		
		int result = 0;
		
		for(int i = 0; i < skipRows; i++)
		{
			rowIter.next();
			result++;
		}
		
		return result;
	}

	private Sheet loadingSheet(IConfigure configure)
	{
		Sheet sheet = null;
		
		try
		{

			sheet = ExcelReader.INSTANCE().getSheet(configure.getSourceFilePath(),
					configure.getSourceSheetIndex());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return sheet;
	}

	private void updateDBEntities(List<IEntity> list, IConfigure configure)
	{
		if(list.isEmpty())
		{
			return;
		}
		
		IPersistanceManager manager = new ObjectDBManager();
		manager.connectDB(StringConstants.EMPTY_STRING, 0, configure.getDBName());
		manager.addOrUpdateEntityList(list);
	}
	
	public List<Ticket> getAllTickets()
	{
		IPersistanceManager manager = new ObjectDBManager();
		manager.connectDB(StringConstants.EMPTY_STRING, 0, IConstants.DB_ROW_DATA);
		TypedQuery<Ticket> query = manager.getEntityManager().createQuery("SELECT c FROM Ticket c", Ticket.class);
		return query.getResultList();
	}
	
	public Ticket getTicket(int number)
	{
		logger.log(Log.LEVEL_INFO, "Loaing the ticket with the number %1", String.valueOf(number));
		IPersistanceManager manager = new ObjectDBManager();
		manager.connectDB(StringConstants.EMPTY_STRING, 0, IConstants.DB_ROW_DATA);
		TypedQuery<Ticket> query = manager.getEntityManager().createQuery("SELECT c FROM Ticket c Where c.number="+number, Ticket.class);
		
		if( null == query || query.getResultList().size() == 0 )
		{
			logger.log(Log.LEVEL_ERROR, "Found no ticket with the number %1", String.valueOf(number));
			return null;
		}
		
		return query.getSingleResult();
	}
	
	public void printAllTicket()
	{
		printObjects(getAllTickets());
	}
	
	private String getCellValueAsString(Cell cell)
	{
		String result = StringConstants.EMPTY_STRING;
		
		if( null != cell )
		{
			if(cell.getCellTypeEnum() == CellType.NUMERIC)
			{
				result = String.valueOf(cell.getNumericCellValue());
			}
			else if(cell.getCellTypeEnum() == CellType.STRING)
			{
				result = cell.getStringCellValue();
			}
		}
		
		return result;
	}
	
	private void printObjects(List<?> list)
	{
		if( !list.isEmpty() )
		{
//			for(int i = 0; i < list.size(); i++ )
//			{
				Ticket ticket = (Ticket) list.get(1);
				System.out.println(ticket.toString());
//			}
		}
	}

	public List<MustFix> getAllMustFixList()
	{
		IPersistanceManager manager = new ObjectDBManager();
		manager.connectDB(StringConstants.EMPTY_STRING, 0, IConstants.DB_MUAS_FIX);
		TypedQuery<MustFix> query = manager.getEntityManager().createQuery("SELECT c FROM MustFix c", MustFix.class);
		return query.getResultList();
	}
	
	public List<TicketComment> getComments(int ticketNumber)
	{
		if( ticketNumber > 0 )
		{
			IPersistanceManager manager = new ObjectDBManager();
			manager.connectDB(StringConstants.EMPTY_STRING, 0, IConstants.DB_TICKET_COMMENT);
			TypedQuery<TicketComment> query = manager.getEntityManager().createQuery("SELECT c FROM TicketComment c where c.number="+ticketNumber, TicketComment.class);
			return query.getResultList();
		}
		
		return new ArrayList<TicketComment>();
	}
}