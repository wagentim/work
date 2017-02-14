package cn.wagentim.work.controller;

import java.util.List;

import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.Sheet;
import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;
import cn.wagentim.work.filter.ISelector;


public interface IController
{
	Header[] getColumnHeaders();

	List<String[]> getTableContents(boolean fromDB);

	Ticket getSelectedTicket(int selectedTicketNumber);

	String getTotalDisplayedTicketNumber();
	
	void updateRecord(IEntity entity);
	
	void addSelectors(ISelector selector);
	
	void clearSelectors();
	
	void setSearchContent(String content);
	
	List<Sheet> getAllSheets();
	
	void deleteEntity(String db, String entity, String column, String value, Class clazz);
}
