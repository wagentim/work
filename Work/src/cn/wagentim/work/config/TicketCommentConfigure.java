package cn.wagentim.work.config;

import cn.wagentim.basicutils.StringConstants;

public class TicketCommentConfigure implements ITicketCommentConfigure
{
	private String excelFilePath = StringConstants.EMPTY_STRING;
	private int kpmIdColumnIndex = -1;
	private int sheetIndex = -1;
	private String sheetDBName = StringConstants.EMPTY_STRING;
	private int ignoreLines = -1;
	private int commentColumnIndex = -1;
	private int statusColumnIndex = -1;
	private int priorityColumnIndex = -1;
	private int nextStepColumnIndex = -1;

	@Override
	public String getSourceFilePath()
	{
		return excelFilePath;
	}

	@Override
	public int getSourceKPMIdColumnIndex()
	{
		return kpmIdColumnIndex;
	}
	
	@Override
	public int getSourceSheetIndex()
	{
		return sheetIndex;
	}

	@Override
	public String getDBName()
	{
		return sheetDBName;
	}

	@Override
	public int getFirstSkippedRows()
	{
		return ignoreLines;
	}

	@Override
	public int getCommentColumnIndex()
	{
		return commentColumnIndex;
	}

	@Override
	public int getStatusColumnIndex()
	{
		return statusColumnIndex;
	}

	public String getExcelFilePath()
	{
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath)
	{
		this.excelFilePath = excelFilePath;
	}

	public int getKpmIdColumnIndex()
	{
		return kpmIdColumnIndex;
	}

	public void setKpmIdColumnIndex(int kpmIdColumnIndex)
	{
		this.kpmIdColumnIndex = kpmIdColumnIndex;
	}

	public int getSheetIndex()
	{
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex)
	{
		this.sheetIndex = sheetIndex;
	}

	public String getSheetDBName()
	{
		return sheetDBName;
	}

	public void setSheetDBName(String sheetDBName)
	{
		this.sheetDBName = sheetDBName;
	}

	public int getIgnoreLines()
	{
		return ignoreLines;
	}

	public void setIgnoreLines(int ignoreLines)
	{
		this.ignoreLines = ignoreLines;
	}

	public void setCommentColumnIndex(int commentColumnIndex)
	{
		this.commentColumnIndex = commentColumnIndex;
	}

	public void setStatusColumnIndex(int statusColumnIndex)
	{
		this.statusColumnIndex = statusColumnIndex;
	}

	@Override
	public int getPriorityColumnIndex()
	{
		// TODO Auto-generated method stub
		return priorityColumnIndex;
	}

	@Override
	public int getNextStepColumnIndex()
	{
		// TODO Auto-generated method stub
		return nextStepColumnIndex;
	}

	public void setPriorityColumnIndex(int priorityColumnIndex)
	{
		this.priorityColumnIndex = priorityColumnIndex;
	}

	public void setNextStepColumnIndex(int nextStepColumnIndex)
	{
		this.nextStepColumnIndex = nextStepColumnIndex;
	}

}
