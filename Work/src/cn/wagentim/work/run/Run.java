package cn.wagentim.work.run;

import cn.wagentim.work.config.IConfigure;
import cn.wagentim.work.config.RawDataConfigureCN;
import cn.wagentim.work.config.RawDataConfigureJP;
import cn.wagentim.work.config.RawDataConfigureKR;
import cn.wagentim.work.config.RawDataConfigureTW;
import cn.wagentim.work.importer.DataDBImporter;

public class Run
{
	private static final IConfigure RAW_DATA_CONFIGURE_CN = new RawDataConfigureCN();
	private static final IConfigure RAW_DATA_CONFIGURE_JP = new RawDataConfigureJP();
	private static final IConfigure RAW_DATA_CONFIGURE_TW = new RawDataConfigureTW();
	private static final IConfigure RAW_DATA_CONFIGURE_KR = new RawDataConfigureKR();
	
	private IConfigure[] RAW_DATA_LIST = {RAW_DATA_CONFIGURE_CN, RAW_DATA_CONFIGURE_JP, RAW_DATA_CONFIGURE_TW, RAW_DATA_CONFIGURE_KR};

//	public static void main(String[] args)
//	{
//		Run run = new Run();
//		run.updateDBData();
//	}

	private  void updateDBData()
	{
		DataDBImporter helper = new DataDBImporter();
		
//		helper.readDataToDB(new SvenConfigure());
		for(int i = 0; i < RAW_DATA_LIST.length; i++ )
		{
			helper.readDataToDB(RAW_DATA_LIST[i]);
		}
	}
}
