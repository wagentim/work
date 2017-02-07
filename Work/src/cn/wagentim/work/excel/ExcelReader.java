package cn.wagentim.work.excel;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.wagentim.basicutils.Validator;

/**
 * Read xls and xlsx format file
 * @author ehuabi0
 *
 */
public final class ExcelReader implements ExcelHandler
{

	private final static ExcelReader reader = new ExcelReader();
	
	public static final ExcelReader INSTANCE()
	{
		return reader;
	}
	
	public final Workbook getWorkbook(String filePath) throws IOException
	{
		if (filePath.trim().toLowerCase().endsWith("xls"))
		{
			return new HSSFWorkbook(new FileInputStream(filePath));
		} 
		else if (filePath.trim().toLowerCase().endsWith("xlsx"))
		{
			return new XSSFWorkbook(new FileInputStream(filePath));
		} 
		else
		{
			throw new IllegalArgumentException("Only support xls and xlsx format");
		}
	}
	
	public final Sheet getSheet(String filePath, String sheetName) throws IOException
	{
		if(Validator.isNullOrEmpty(sheetName))
		{
			return null;
		}
		
		return getWorkbook(filePath).getSheet(sheetName);
	}
	
	public final Sheet getSheet(String filePath, int sheetIndex) throws IOException
	{
		if(sheetIndex < 0)
		{
			return null;
		}
		
		return getWorkbook(filePath).getSheetAt(sheetIndex);
	}
}
