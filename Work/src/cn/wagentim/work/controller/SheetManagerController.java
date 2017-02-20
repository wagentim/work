package cn.wagentim.work.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.Sheet;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.ISelector;

public class SheetManagerController extends AbstractController
{
	private int ticketNumber;
	private static final Header[] TABLE_HEADERS = new Header[]{
			new Header("ID", 30),
			new Header("Date", 150),
			new Header("Sheet Name", 200)
			};
	
	@Override
	public Header[] getColumnHeaders()
	{
		return TABLE_HEADERS;
	}
	
	@Override
	public List<String[]> getTableContents(boolean fromDB)
	{
		@SuppressWarnings("unchecked")
		List<Sheet> list = (List<Sheet>) importer.getAllRecord(IConstants.DB_SHEET, "Sheet", Sheet.class);
		ticketNumber = list.size();
		
		List<String[]> result = new ArrayList<String[]>();
		
		long id;
		String date;
		String sheetName;
		int counter = 1;
		
		for(Sheet t : list)
		{
			
			if( null == t )
			{
				id = -1;
				date = StringConstants.EMPTY_STRING;
				sheetName = StringConstants.EMPTY_STRING;
			}
			else
			{
				id = counter++;
				date = SheetTicketController.sdf.format(new Date(t.getTime()));
				sheetName = t.getName();
			}
			
			result.add(new String[]{String.valueOf(id), date, sheetName});
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
		importer.updateEntity(entity, IConstants.DB_SHEET);
	}

	@Override
	public void addSelectors(ISelector selector)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearSelectors()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSearchContent(String content)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sheet> getAllSheets()
	{
		return null;
	}

	@Override
	public void deleteEntity(String db, String entity, String column, String value, Class clazz)
	{
		importer.deleteEntity(db, entity, column, value, clazz);
	}

	@Override
	public void addTicketComment(String dbName, int kpmid)
	{
		// TODO Auto-generated method stub
		
	}

	
}
