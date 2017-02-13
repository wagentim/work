package cn.wagentim.work.config;

import cn.wagentim.work.datenbank.IDBConstants;

public class SvenConfigure implements IConfigure
{
	final private String sourceFilePath = "d:/Work/KPM List/Cluster 8/sven.xlsx";
	final private int sourceKeyColumnIndex = 1;
	final private int sourceSheetIndex = 0;
	
	@Override
	public String getSourceFilePath()
	{
		return sourceFilePath;
	}

	@Override
	public String getTargetFilePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSourceKeyColumnIndex()
	{
		// TODO Auto-generated method stub
		return sourceKeyColumnIndex;
	}

	@Override
	public int getTargetKeyColumnIndex()
	{
		return sourceSheetIndex;
	}

	@Override
	public int getSourceSheetIndex()
	{
		return sourceSheetIndex;
	}

	@Override
	public int getTargetSheetIndex()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDBName()
	{
		// TODO Auto-generated method stub
		return "sven.odb";
	}

	@Override
	public int getFirstSkippedRows()
	{
		// TODO Auto-generated method stub
		return 1;
	}
}