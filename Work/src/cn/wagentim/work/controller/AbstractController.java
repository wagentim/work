package cn.wagentim.work.controller;

import java.util.List;

import cn.wagentim.work.exporter.ExcelFileExporter;
import cn.wagentim.work.importer.ImportTickets;

public abstract class AbstractController implements IController
{
	protected final ImportTickets importer = new ImportTickets();
	
	protected final ExcelFileExporter excelExporter = new ExcelFileExporter();
	
	public ImportTickets getDBImporter()
	{
		return importer;
	}
	
	public void saveDataToExcelFile(String fileName, String[] headers, List<String[]> currentTableContent)
	{
		excelExporter.setTargetFile(fileName);
		excelExporter.setSheetName("Test");
		excelExporter.setTableHeaders(headers);
		excelExporter.setTableContents(currentTableContent);
		excelExporter.exportTalbeContentToExcelFile();
	}
}
