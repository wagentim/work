package cn.wagentim.work.config;

import java.text.SimpleDateFormat;

public class IConstants
{
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");
	
	/** Title */
	public static final float VERSION = 0.1f;
	public static final String TITLE = "KPM Ticket Viewer "+String.valueOf(VERSION) + " HB";
	
	/** Menu File */
	public static final String MENU_FILE = "File";
	
	/** Menu Filter */
	public static final String MENU_FILTER = "Filter";
	
	public static final String MENU_FILTER_RATING = "Rating Filter";
	public static final String MENU_ITEM_RATING_A = "Rating A";
	public static final String MENU_ITEM_RATING_B = "Rating B";
	public static final String MENU_ITEM_RATING_C = "Rating C";
	public static final String MENU_ITEM_RATING_D = "Rating D";
	
	public static final String STRING_KPM_ID = "KPM ID";
	public static final String STRING_SHORT_TEXT = "Short Text";
	public static final String STRING_SUPPLIER = "Supplier: ";
	public static final String STRING_PROBLEM_STATUS = "P Status: ";
	public static final String STRING_ENGINEER_STATUS = "E Status: ";
	
	
	public static final String STRING_TICKET_ID = "Ticket ID";


	public static final String DB_MUAS_FIX = "mustfix.odb";
	public static final String DB_TICKET = "tickets.odb";
	public static final String DB_TICKET_COMMENT = "ticketcomments.odb";
	public static final String DB_SVEN = "sven.odb";
	public static final String DB_SHEET = "sheet.odb";
	public static final String DB_SURFIX = ".odb";

	public static final String String_SHEET_INDEX = "Sheet Index";

	public static final String String_IGNORE_LINES = "Ignore Lines";

	public static final String String_KPM_ID_INDEX = "KPM ID Index";

	public static final String String_COMMENT_INDEX = "Comment Index";

	public static final String String_STATUS_INDEX = "Status Index";

	public static final String String_PRIORITY_INDEX = "Priority Index";

	public static final String String_NEXT_STEP_INDEX = "NextStep Index";

	public static final String String_NO_TICKET_FIND_IN_DB = "Cannot find ticket in the DB";
	
	public static final String STRING_SAVE_TO_EXCEL = "Save To Excel File";
	
	public static final String STRING_IMPORT_TICKETS = "Import Tickets";
	
	public static final SimpleDateFormat SINGLE_DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

	public static final String STRING_HEADER_KPM = "KPM";

	public static final String STRING_HEADER_MARKET = "Market";

	public static final String LOAD_MAIN_TICKET = "Load Main DB";

	/** Rating */
	public static final String STRING_HEADER_RATING = "Rating";
	public static final String STRING_RATING_A = "A";
	public static final String STRING_RATING_B = "B";
	public static final String STRING_RATING_C = "C";
	public static final String STRING_RATING_D = "D";


	

	

	
}
