package cn.wagentim.work.output;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import cn.wagentim.work.filter.ISelector;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

/**
 * Generate required documents and data from the raw data. The configuration
 * file will
 * 
 * @author ehuabi0
 *
 */
public class OutPrinter
{
	private LogChannel logger = QLoggerService
			.getChannel(QLoggerService.addChannel(new DefaultChannel(OutPrinter.class.getSimpleName())));


	private List<Integer> getNewAddedIds(List<Integer> sourceKPMIds, List<Integer> targetKPMIds)
	{
		List<Integer> result = new ArrayList<Integer>();
		Integer value = 0;

		for (int i = 0; i < sourceKPMIds.size(); i++)
		{
			if (!targetKPMIds.contains(value = sourceKPMIds.get(i)))
			{
				result.add(value);
			}
		}

		return result;
	}

	private List<Integer> getDeletedIds(List<Integer> sourceKPMIds, List<Integer> targetKPMIds)
	{
		List<Integer> result = new ArrayList<Integer>();
		Integer value = 0;

		for (int i = 0; i < targetKPMIds.size(); i++)
		{
			if (!sourceKPMIds.contains(value = targetKPMIds.get(i)))
			{
				result.add(value);
			}
		}

		return result;
	}

	private List<Integer> getKeyList(Sheet sheet, int keyIndex, ISelector selector)
	{
		final List<Integer> result = new ArrayList<Integer>();

		int start = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();

		if (0 > start || 0 > end || end < start)
		{
			logger.log(Log.LEVEL_ERROR, "Wrong Sheet row index with start %1 and end %2", String.valueOf(start),
					String.valueOf(end));
		}
		else
		{
			for (int i = start; i < end; i++)
			{
				Row row = sheet.getRow(i);

				if (null != selector)
				{
//					row = selector.check(row);
				}
				
				if (null == row)
				{
//					logger.log(Log.LEVEL_WARN, "The Row %1 is null!", String.valueOf(i));
					continue;
				}

				Cell cell = row.getCell(keyIndex);
				if (cell.getCellTypeEnum() == CellType.NUMERIC)
				{
					result.add(Integer.valueOf((int) cell.getNumericCellValue()));
				}

			}
		}

		return result;
	}

	private void printKPMIDs(List<Integer> ids)
	{
		if (!ids.isEmpty())
		{
			for (int i = 0; i < ids.size(); i++)
			{
				System.out.println(ids.get(i));
			}
		}
	}
}
