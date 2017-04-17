package cn.wagentim.work.filter;

import java.util.List;

import cn.wagentim.entities.work.TicketEntity;

public interface ISelector
{

	List<TicketEntity> check(List<TicketEntity> list);
	
	void setSearchContent(List<String> content);
	void setSearchContent(String content);
	
	boolean isExclusive();

	void setExclusive(boolean isExclusive);

}
