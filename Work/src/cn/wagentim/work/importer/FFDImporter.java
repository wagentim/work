package cn.wagentim.work.importer;

import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.service.QLoggerService;

public class FFDImporter implements IImporter
{

	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(FFDImporter.class.getSimpleName())));
	
	@Override
	public void exec()
	{
		
	}

}
