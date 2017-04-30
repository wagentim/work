package cn.wagentim.work.controller;

import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.exporter.ExcelFileExporter;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.filter.SelectorManager;
import cn.wagentim.work.importer.ImportTickets;

public abstract class AbstractController implements IController
{
	protected SelectorManager selMgr = new SelectorManager();
//	protected List<ISelector> selectors = new ArrayList<ISelector>();
	protected List<ISelector> savedSelectors = null;
	
	protected final ImportTickets importer = new ImportTickets();
	protected final ExcelFileExporter excelExporter = new ExcelFileExporter();
	
	public ImportTickets getDBImporter()
	{
		return importer;
	}
	
	public void saveDataToExcelFile(String fileName, List<String> headers, List<List<String>> currentTableContent)
	{
		excelExporter.setTargetFile(fileName);
		excelExporter.setSheetName("Test");
		excelExporter.setTableHeaders(headers);
		excelExporter.setTableContents(currentTableContent);
		excelExporter.exportTalbeContentToExcelFile();
	}
	
	@Override
	public void addSearchContent(int selector, String content)
	{
		if( IConstants.SELECTOR_KPM_ID == selector )
		{
			selMgr.saveCurrentSelectors();
		}
		else if( IConstants.SELECTOR_TEXT_DISABLE == selector )
		{
			selMgr.restoreSelectors();
			return;
		}
		
		ISelector sele = selMgr.getSelector(selector);
		
		if( !Validator.isNullOrEmpty(content) )
		{
			sele.addSearchContent(content);
		}
	}
	
	
	public void removeSelector(int selector)
	{
		selMgr.removeSelector(selector);
	}
	
	public void clearSelectors()
	{
		selMgr.clearAllSelectors();
	}
	
	
}
