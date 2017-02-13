package cn.wagentim.work.filter;

import java.util.List;

import cn.wagentim.entities.work.Ticket;

public interface ISelector
{

	List<Ticket> check(List<Ticket> list);

}
