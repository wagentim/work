package de.eso.modelmaker.controller;

import java.util.List;

import cn.wagentim.entities.work.Ticket;
import cn.wagentim.work.entity.Header;


public interface IController
{
	Header[] getColumnHeaders();

	List<String[]> getTableContents(boolean fromDB);

	Ticket getSelectedTicket(int selectedTicketNumber);

	String getTotalDisplayedTicketNumber();
}
