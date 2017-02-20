package cn.wagentim.work.config;

public interface IImportConfigure
{
	String getSourceFilePath();
	int getSourceKPMIdColumnIndex();
	int getSourceSheetIndex();
	String getDBName();
	int getFirstSkippedRows();
}
