package cn.wagentim.work.filter;

import java.util.List;

import cn.wagentim.entities.work.TicketEntity;

public interface ISelector
{
	List<TicketEntity> check(List<TicketEntity> list);
	void addSearchContent(String content);
	void removeSearchContent(String content);
	List<String> getSearchContent();
	int getSelectorType();
}
