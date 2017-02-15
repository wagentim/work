package cn.wagentim.work.listener;

import java.util.List;

import cn.wagentim.entities.work.Sheet;

public interface ISearchTableListener
{
	void selectedTicketNumber(int selectedTicketNumber);
	void selectedSearchItem(String item);
	void setSearchContent(String content);
	List<Sheet> getAllSheet();
	void addTicketToSheet(String dbName, int kpmid);
}
