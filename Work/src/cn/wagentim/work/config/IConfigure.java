package cn.wagentim.work.config;

public interface IConfigure
{
	String getSourceFilePath();
	String getTargetFilePath();
//	void setSourceFilePath(String sourceFilePath);
//	void setTargetFilePath(String targetFilePath);
	
	int getSourceKPMIdColumnIndex();
	int getTargetKPMIdColumnIndex();
//	void setSourceKeyColumnIndex(int sourceKeyColumnIndex);
//	void setTargetKeyColumnIndex(int targetKeyColumnIndex);
	
	int getSourceSheetIndex();
	int getTargetSheetIndex();
	String getDBName();
	int getFirstSkippedRows();
//	void setSourceSheetIndex(int sourceSheetIndex);
//	void setTargetSheetIndex(int targetSheetIndex);
}
