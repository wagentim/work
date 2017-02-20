package cn.wagentim.work.exporter;

import cn.wagentim.basicutils.StringConstants;

public abstract class AbstractExporter implements IExporter
{
	protected String fileLocation = StringConstants.EMPTY_STRING;
	protected String sheetName = StringConstants.EMPTY_STRING;
	
	public void setTargetFile(String file)
	{
		this.fileLocation = file;
	}

	public String getSheetName()
	{
		return sheetName;
	}

	public void setSheetName(String sheetName)
	{
		this.sheetName = sheetName;
	}
}
