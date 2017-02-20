package cn.wagentim.work.config;

public class RawDataConfigureTW implements IImportConfigure
{
	final private String sourceFilePath = "d:/Work/KPM List/Cluster 8/original_tw.xlsx";
	final private int sourceKeyColumnIndex = 0;
	final private int sourceSheetIndex = 0;
	
	@Override
	public String getSourceFilePath()
	{
		return sourceFilePath;
	}

	public int getSourceKPMIdColumnIndex()
	{
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return IConstants.DB_TICKET;
	}

	@Override
	public int getFirstSkippedRows()
	{
		// TODO Auto-generated method stub
		return 1;
	}
}
