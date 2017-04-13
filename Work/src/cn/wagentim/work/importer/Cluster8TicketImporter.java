package cn.wagentim.work.importer;

import cn.wagentim.work.config.IImportConfigure;
import cn.wagentim.work.config.RawDataConfigureCN;

public class Cluster8TicketImporter implements IImporter
{

	private static final IImportConfigure RAW_DATA_CONFIGURE_CN = new RawDataConfigureCN();
//	private static final IImportConfigure RAW_DATA_CONFIGURE_JP = new RawDataConfigureJP();
//	private static final IImportConfigure RAW_DATA_CONFIGURE_TW = new RawDataConfigureTW();
//	private static final IImportConfigure RAW_DATA_CONFIGURE_KR = new RawDataConfigureKR();
//	private static final IImportConfigure RAW_DATA_CONFIGURE_EG_GQ = new RawDataConfigureEGGQ();
	
	private IImportConfigure[] RAW_DATA_LIST = {RAW_DATA_CONFIGURE_CN};
	
	@Override
	public void exec()
	{
		ImportTickets helper = new ImportTickets();
		
		for(int i = 0; i < RAW_DATA_LIST.length; i++ )
		{
			helper.importStandardTicket(RAW_DATA_LIST[i]);
		}
	}

}
