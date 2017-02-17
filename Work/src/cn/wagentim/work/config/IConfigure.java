package cn.wagentim.work.config;

public interface IConfigure
{
	String getSourceFilePath();
	String getTargetFilePath();
	
	int getSourceKPMIdColumnIndex();
	int getTargetKPMIdColumnIndex();
	
	int getSourceSheetIndex();
	int getTargetSheetIndex();
	String getDBName();
	int getFirstSkippedRows();
}
