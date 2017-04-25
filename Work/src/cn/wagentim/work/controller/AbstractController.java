package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.exporter.ExcelFileExporter;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.filter.KPMIDSelector;
import cn.wagentim.work.filter.RatingSelector;
import cn.wagentim.work.filter.SupplierSelector;
import cn.wagentim.work.importer.ImportTickets;

public abstract class AbstractController implements IController
{
	protected List<ISelector> selectors = new ArrayList<ISelector>();
	
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
		if( Validator.isNull(content) )
		{
			// add log here
			return;
		}
		
		ISelector sele = getSelector(selector);
		
		if( Validator.isNull(sele) )
		{
			sele = createNewSelector(selector);
			selectors.add(sele);
		}
		
		sele.addSearchContent(content);
	}
	
	protected ISelector getSelector(int selector)
	{
		Iterator<ISelector> it = selectors.iterator();
		ISelector result = null;
		
		while(it.hasNext())
		{
			ISelector tmp = it.next();
			
			if( tmp.getSelectorType() == selector )
			{
				result = tmp;
				break;
			}
		}
		
		return result;
	}

	private ISelector createNewSelector(int selector)
	{
		switch(selector)
		{
			case IConstants.SELECTOR_KPM_ID:
				return new KPMIDSelector();
			
			case IConstants.SELECTOR_RATING:
				return new RatingSelector();
				
			case IConstants.SELECTOR_SUPPLIER:
				return new SupplierSelector();
				
			default:
				return null;
		}
	}
	
	public void removeSelector(int selector)
	{
		Iterator<ISelector> it = selectors.iterator();
		
		while(it.hasNext())
		{
			ISelector tmp = it.next();
			
			if( tmp.getSelectorType() == selector )
			{
				it.remove();
				break;
			}
		}
	}
	
	public void clearSelectors()
	{
		selectors.clear();
	}
}
