package cn.wagentim.work.listener;

public interface ISearchTableListener
{
	void selectedTicketNumber(int selectedTicketNumber);
	void selectedSearchItem(String item);
	void setSearchContent(String content);
}
