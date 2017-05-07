package cn.wagentim.work.controller;

import java.util.List;

import cn.wagentim.entities.web.IEntity;
import cn.wagentim.entities.work.SheetEntity;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.entity.Header;


public interface IController
{
	Header[] getColumnHeaders();

	List<String[]> getTableContents(boolean fromDB);

	TicketEntity getSelectedTicket(int selectedTicketNumber);

	String getTotalDisplayedTicketNumber();
	
	void updateRecord(IEntity entity);
	
	void addSearchContent(int selector, String content);
	
	List<SheetEntity> getAllSheets();
	
	void deleteEntity(String db, String entity, String column, String value, Class clazz);

	void addTicketComment(String dbName, int kpmid);

	void saveDataToExcelFile(String fileName, List<String> headers, List<List<String>> currentTableContent);

	void columnSelected(String columnName);

	void decorateOutput(List<String> headers, List<List<String>> currentTableContent);

	void removeSelector(int selector);

	void clearSelectors();

	void removeSelector(int selector, String selectorName);
}
