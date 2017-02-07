package cn.wagentim.work.output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import cn.wagentim.work.config.IConfigure;
import cn.wagentim.work.excel.ExcelReader;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.filter.MustFixFilter;
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

	public void compareMustFixTicketList(IConfigure configure)
	{
		Sheet sourceSheet = null;
		Sheet targetSheet = null;

		try
		{
			sourceSheet = new ExcelReader().getSheet(configure.getSourceFilePath(),
					configure.getSourceSheetIndex());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (null == sourceSheet)
		{
			logger.log(Log.LEVEL_ERROR, "Cannot get source sheet from the file %1 with the sheet index %2",
					configure.getSourceFilePath(), String.valueOf(0));
			return;
		}

		System.out.println(sourceSheet.getSheetName());

		try
		{
			System.out.println(configure.getTargetFilePath());
			targetSheet = new ExcelReader().getSheet(configure.getTargetFilePath(),
					configure.getTargetSheetIndex());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (null == targetSheet)
		{
			logger.log(Log.LEVEL_ERROR, "Cannot get target sheet from the file %1 with the sheet index %2",
					configure.getTargetFilePath(), String.valueOf(0));
			return;
		}
		System.out.println(targetSheet.getSheetName());
		// Strategy is to add new ticket from source file into target file and
		// mark the remove ticket

		int sourceKeyIndex = configure.getSourceKeyColumnIndex();
		int targetKeyIndex = configure.getTargetKeyColumnIndex();

		if (0 > sourceKeyIndex || 0 > targetKeyIndex)
		{
			logger.log(Log.LEVEL_ERROR, "Key Index is incorrect with source: %1 and target %2",
					String.valueOf(sourceKeyIndex), String.valueOf(targetKeyIndex));
			return;
		}

		// step 1: cache all keys from target file

//		System.out.println(targetSheet.getSheetName());
		
		List<Integer> sourceKPMIds = getKeyList(sourceSheet, sourceKeyIndex, new MustFixFilter());
		List<Integer> targetKPMIds = getKeyList(targetSheet, targetKeyIndex, null);
		
		List<Integer> newAdded = getNewAddedIds(sourceKPMIds, targetKPMIds);
		List<Integer> removed = getDeletedIds(sourceKPMIds, targetKPMIds);

//		printKPMIDs(sourceKPMIds);
		printKPMIDs(newAdded);
		System.out.println("-------------------------------------");
//		printKPMIDs(targetKPMIds);
		printKPMIDs(removed);

	}

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
					row = selector.check(row);
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
