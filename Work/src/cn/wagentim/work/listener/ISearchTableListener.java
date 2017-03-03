package cn.wagentim.work.listener;

import java.util.List;

import cn.wagentim.entities.work.SheetEntity;

public interface ISearchTableListener
{
	void selectedTicketNumber(int selectedTicketNumber);
	void selectedSearchItem(String item);
	void setSearchContent(String content);
	List<SheetEntity> getAllSheet();
	void addTicketToSheet(String dbName, int kpmid);
	void columnSelection(String columnName);
	boolean shouldShowDeleteTicketOption();
	void deleteSheetTicket(List<Integer> selectedTicketNumber);
}
