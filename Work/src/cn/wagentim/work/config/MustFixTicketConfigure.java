package cn.wagentim.work.config;

import cn.wagentim.basicutils.StringConstants;

public class MustFixTicketConfigure implements IImportConfigure
{
	final private String sourceFilePath = "d:/Work/KPM List/Cluster 8/MustFixList.xlsx";
	final private String targetFilePath = StringConstants.EMPTY_STRING;
	final private int sourceKeyColumnIndex = 0;
	final private int targetKeyColumnIndex = -1;
	final private int sourceSheetIndex = 1;
	final private int targetSheetIndex = -1;
	
	@Override
	public String getSourceFilePath()
	{
		return sourceFilePath;
	}

	@Override
	public int getSourceKPMIdColumnIndex()
	{
		return sourceKeyColumnIndex;
	}

	@Override
	public int getSourceSheetIndex()
	{
		return sourceSheetIndex;
	}

	@Override
	public String getDBName()
	{
		return IConstants.DB_MUAS_FIX;
	}

	@Override
	public int getFirstSkippedRows()
	{
		return 2;
	}
}
