package cn.wagentim.work.importer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.Comment;
import cn.wagentim.entities.work.SheetTicketEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.managers.IPersistanceManager;
import cn.wagentim.managers.ObjectDBManager;
import cn.wagentim.work.config.IImportConfigure;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.config.ITicketCommentConfigure;
import cn.wagentim.work.excel.ExcelReader;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class ExcelFileImporter implements IImporter
{

	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(ExcelFileImporter.class.getSimpleName())));
	
	private IImportConfigure configure;
	
	public ExcelFileImporter(IImportConfigure configure)
	{
		this.configure = configure;
	}
	
	public IImportConfigure getConfigure()
	{
		return configure;
	}

	public void setConfigure(IImportConfigure configure)
	{
		this.configure = configure;
	}

	@Override
	public void exec()
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
	
	private void updateDBEntities(List<IEntity> list, IImportConfigure configure)
	{
		if(list.isEmpty())
		{
			return;
		}
		
		IPersistanceManager manager = new ObjectDBManager();
		manager.connectDB(StringConstants.EMPTY_STRING, 0, configure.getDBName());
		manager.addOrUpdateEntityList(list);
	}
	
	private IEntity assignValues(IImportConfigure configure, Row myRow)
	{
		if( configure instanceof ITicketCommentConfigure )
		{
			return assignSheetTicket((ITicketCommentConfigure)configure, myRow);
		}
		else
		{
			return assignTicketValues(myRow);
		}
	}
	
	private String decorateMarket(String cellValueAsString)
	{
		String result = StringConstants.EMPTY_STRING;
		
		if( !StringConstants.EMPTY_STRING.equals(cellValueAsString))
		{
			if( cellValueAsString.contains("China") )
			{
				result = "CN";
			}
			else if( cellValueAsString.contains("Japan") )
			{
				result = "JP";
			}
			else if( cellValueAsString.contains("South Korea") )
			{
				result = "KR";
			}
			else if( cellValueAsString.contains("Taiwan") )
			{
				result = "TW";
			}
		}
		return result;
	}	
	
	private SheetTicketEntity assignSheetTicket(ITicketCommentConfigure configure, Row myRow)
	{
		SheetTicketEntity sheetTicket = new SheetTicketEntity();
		
		Iterator cellIter = myRow.cellIterator();
		
		int kpmIndex = configure.getSourceKPMIdColumnIndex();
		int commentIndex = configure.getCommentColumnIndex();
		int statusIndex = configure.getStatusColumnIndex();
		int priorityIndex = configure.getPriorityColumnIndex();
		int nextStepIndex = configure.getNextStepColumnIndex();
		
		String comment = StringConstants.EMPTY_STRING;
		int number = 0;

		while (cellIter.hasNext())
		{

			Cell myCell = (Cell) cellIter.next();
			
			
			int columnIndex = myCell.getColumnIndex();
			
			if(columnIndex == kpmIndex)
			{
				number = getCellValueAsInteger(myCell);
				sheetTicket.setKpmID(number);
			}
			else if(columnIndex == commentIndex)
			{
				comment = getCellValueAsString(myCell);
			}
			else if(columnIndex == statusIndex)
			{
				sheetTicket.setStatus(getCellValueAsString(myCell));
			}
			else if(columnIndex == priorityIndex)
			{
				sheetTicket.setPriority(getCellValueAsInteger(myCell));
			}
			else if(columnIndex == nextStepIndex)
			{
				sheetTicket.setNextStep(getCellValueAsString(myCell));
			}
		}
		
		return sheetTicket;
	}
	
	private Set<Comment> getComments(SheetTicketEntity sheetTicket, String cellValueAsString)
	{
		Set<Comment> result = new HashSet<Comment>();
		Comment tc = null;
		
		if( !cellValueAsString.isEmpty() )
		{
			StringBuffer timeAndAuthor = new StringBuffer();
			StringBuffer comment = new StringBuffer();
			boolean isTime = false;
			
			for(int i = 0; i < cellValueAsString.length(); i++)
			{
				char c = cellValueAsString.charAt(i);
				
				switch (c)
				{
					case '[':
						if( null != (tc = parserComment(sheetTicket, timeAndAuthor.toString(), comment.toString()))) result.add(tc) ;
						isTime = true;
						comment.delete(0, comment.length());
						timeAndAuthor.delete(0, timeAndAuthor.length());
						break;
					case ']':
						isTime = false;
						break;
					default:
						if(isTime)
						{
							timeAndAuthor.append(c);
						}
						else
						{
							comment.append(c);
						}
						
				}
				
				if( i == cellValueAsString.length() - 1 )
				{
					if( null != (tc = parserComment(sheetTicket, timeAndAuthor.toString(), comment.toString()))) result.add(tc) ;
				}
			}
		}
		
		return result;
	}
	
	private Comment parserComment(SheetTicketEntity sheetTicket, String timeAndAuthor, String comment)
	{
		if( timeAndAuthor.length() <=0 && comment.length() <= 0 )
		{
			return null;
		}
		
		StringTokenizer st = new StringTokenizer(timeAndAuthor.toString(), ",");
		Comment tc = new Comment();
		
		if( st.countTokens() != 2 )
		{
			logger.log(Log.LEVEL_ERROR, "Time and Author for the ticket %1 is in wrong format", "" + sheetTicket.getKpmID());
			tc.setAuthor(timeAndAuthor.toString());
			tc.setTime(0);
		}
		else
		{
			tc.setTime(parserTime(st.nextToken()));
			tc.setAuthor(st.nextToken());
		}
		
		tc.setComment(comment.toString());
//		tc.setSheetTicket(sheetTicket);
		return tc;
	}
	
	private long parserTime(String time)
	{
		Date date = null;
		
		try
		{
			date = IConstants.SIMPLE_DATE_FORMAT.parse(time);
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

	private TicketEntity assignTicketValues(Row myRow)
	{
		TicketEntity ticket = new TicketEntity();
		Iterator cellIter = myRow.cellIterator();

		while (cellIter.hasNext())
		{

			Cell myCell = (Cell) cellIter.next();
			
			int columnIndex = myCell.getColumnIndex();
			String columnName = CellReference.convertNumToColString(columnIndex);
			
			if(columnName.equals("A"))
			{
				ticket.setAction(getCellValueAsString(myCell));
			}
			else if(columnName.equals("B"))
			{
				int number = getCellValueAsInteger(myCell);
				
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
			else if(columnName.equals("G"))
			{
				ticket.setMarket(decorateMarket(getCellValueAsString(myCell)));
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
			else if(columnName.equals("AL"))
			{
				ticket.setRating(getCellValueAsString(myCell));
			}
			else if(columnName.equals("AM"))
			{
				ticket.setStatus(getCellValueAsInteger(myCell));
			}
			else if(columnName.equals("AN"))
			{
				ticket.setEnginerringStatus(getCellValueAsInteger(myCell));
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
	
	private int getCellValueAsInteger(Cell cell)
	{
		int result = -1;
		
		if( null != cell )
		{
			if(cell.getCellTypeEnum() == CellType.NUMERIC)
			{
				result = (int) cell.getNumericCellValue();
			}
			else if(cell.getCellTypeEnum() == CellType.STRING)
			{
				String s = cell.getStringCellValue();
				if( s.equals("-"))
				{
					s = "0";
				}
				result = Integer.valueOf(s);
			}
		}
		
		return result;
	}

	private Sheet loadingSheet(IImportConfigure configure)
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
	
	private int skipRows(Iterator rowIter, IImportConfigure configure)
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

}
