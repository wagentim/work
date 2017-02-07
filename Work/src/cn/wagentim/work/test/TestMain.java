package cn.wagentim.work.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import cn.wagentim.work.config.IConfigure;
import cn.wagentim.work.config.MustFixTicketConfigure;
import cn.wagentim.work.output.OutPrinter;

public class TestMain
{
	public static void main(String[] args)
	{
		final String sourcePath = "d:/Work/KPM List/Cluster 8/kpm.xlsx";
		final String targetPath = "d:/Work/KPM List/Cluster 8/MustFixList.xlsx";
		
		OutPrinter printer = new OutPrinter();
		
		IConfigure mustfixConfigure = new MustFixTicketConfigure();
//		mustfixConfigure.setSourceKeyColumnIndex(0);
//		mustfixConfigure.setTargetKeyColumnIndex(0);
//		
//		mustfixConfigure.setSourceSheetIndex(3);
//		mustfixConfigure.setTargetSheetIndex(1);
		
		printer.compareMustFixTicketList(mustfixConfigure);
		
/*		
		TestMain tm = new TestMain();

		try
		{
			Sheet sheet = ExcelReader.INSTANCE().getSheet(path, 0);

			if (null == sheet)
			{
				System.out.println("Not successfully read the file!");
				
			} 
			else
			{
				System.out.println("successfully read the file!");
				tm.printSheetRows(sheet);
			}

		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	private void printSheetRows(Sheet sheet)
	{
		int index = 1;
		
		if(null == sheet)
		{
			return;
		}
		
		int rowCounter = sheet.getLastRowNum();
		
		for(int i = 0; i < rowCounter; i++)
		{
			Row row = sheet.getRow(i);
			
			if(null == row)
			{
				continue;
			}
			
			Cell cell = row.getCell(9);
			
			if(null != cell)
			{
				System.out.println(index + " -> " + cell.getStringCellValue());
				index++;
			}
		}
	}
}
