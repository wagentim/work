package cn.wagentim.work.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.wagentim.basicutils.StringConstants;
import cn.wagentim.basicutils.Validator;
import de.wagentim.qlogger.channel.DefaultChannel;
import de.wagentim.qlogger.channel.LogChannel;
import de.wagentim.qlogger.logger.Log;
import de.wagentim.qlogger.service.QLoggerService;

public class ExcelFileExporter extends AbstractExporter
{
	private static final LogChannel logger = QLoggerService.getChannel(QLoggerService.addChannel(new DefaultChannel(ExcelFileExporter.class.getSimpleName())));
	
	private List<String> tableHeaders = Collections.EMPTY_LIST;
	@SuppressWarnings("unused")
	private List<List<String>> tableContents = Collections.EMPTY_LIST;
	
	
	@SuppressWarnings("resource")
	public void exportTalbeContentToExcelFile()
	{
		if(Validator.isNullOrEmpty(fileLocation))
		{
			logger.log(Log.LEVEL_ERROR, "The target file name is null or empty!");
			return;
		}
		
		Path path = Paths.get(fileLocation);
		
		if( null == path )
		{
			logger.log(Log.LEVEL_ERROR, "Cannot get the path object from the give file location: %1", fileLocation);
			return;
		}
		
		if( !Files.exists(path) )
		{
			logger.log(Log.LEVEL_ERROR, "File is not exist in the location: %1", fileLocation);
			
			try
			{
				Files.createFile(path);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		Workbook wb = null;
		
		wb = new XSSFWorkbook();
		
		Sheet exportSheet = wb.createSheet((StringConstants.EMPTY_STRING.equals(sheetName) ? "Exported Sheet" : sheetName));
		
		if( null == exportSheet )
		{
			logger.log(Log.LEVEL_ERROR, "Cannot create Sheet in the workbook: %1", fileLocation);
			return;
		}
		
		if( null !=tableHeaders && tableHeaders.size() > 0)
		{
			writeDataToRow(tableHeaders, exportSheet.createRow(0));
		}
		
		if( null != tableContents && tableContents.size() > 0 )
		{
			int rowIndex = 1;
			
			for(List<String> data : tableContents)
			{
				writeDataToRow(data, exportSheet.createRow(rowIndex++));
			}
		}
		
		try
		{
			FileOutputStream fos = new FileOutputStream(fileLocation);
			wb.write(fos);
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void writeDataToRow(List<String> data, Row row)
	{
		for(int i = 0; i < data.size(); i++)
		{
			row.createCell(i, CellType.STRING).setCellValue(data.get(i));
		}
	}

	public List<String> getTableHeaders()
	{
		return tableHeaders;
	}

	public void setTableHeaders(List<String> tableHeaders)
	{
		this.tableHeaders = tableHeaders;
	}

	public List<List<String>> getTableContents()
	{
		return tableContents;
	}

	public void setTableContents(List<List<String>> tableContents)
	{
		this.tableContents = tableContents;
	}
}
