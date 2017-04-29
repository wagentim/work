package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.work.config.IConstants;
import cn.wagentim.work.exporter.ExcelFileExporter;
import cn.wagentim.work.filter.EngineerStatusSelector;
import cn.wagentim.work.filter.ISelector;
import cn.wagentim.work.filter.KPMIDSelector;
import cn.wagentim.work.filter.MarketSelector;
import cn.wagentim.work.filter.ProblemSolvertSelector;
import cn.wagentim.work.filter.RatingSelector;
import cn.wagentim.work.filter.ShortTextSelector;
import cn.wagentim.work.filter.SupplierSelector;
import cn.wagentim.work.filter.SupplierStatusSelector;
import cn.wagentim.work.importer.ImportTickets;

public abstract class AbstractController implements IController
{
	protected List<ISelector> selectors = new ArrayList<ISelector>();
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
			saveCurrentSelectors();
		}
		else if( IConstants.SELECTOR_TEXT_DISABLE == selector )
		{
			restoreSelectors();
			return;
		}
		
		ISelector sele = getSelector(selector);
		
		if( Validator.isNull(sele) )
		{
			sele = createNewSelector(selector);
			selectors.add(sele);
		}
		
		if( !Validator.isNullOrEmpty(content) )
		{
			sele.addSearchContent(content);
		}
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
				
			case IConstants.SELECTOR_ENGINEER_STATUS:
				return new EngineerStatusSelector();
				
			case IConstants.SELECTOR_MARKET:
				return new MarketSelector();
				
			case IConstants.SELECTOR_SHORT_TEXT:
				return new ShortTextSelector();
				
			case IConstants.SELECTOR_PROBLEM_SOLVER:
				return new ProblemSolvertSelector();
				
			case IConstants.SELECTOR_SUPPLIER_STATUS:
				return new SupplierStatusSelector();
				
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
	
	public void saveCurrentSelectors()
	{
		savedSelectors = selectors;
		selectors.clear();
	}
	
	public void restoreSelectors()
	{
		if( null != savedSelectors )
		{
			selectors = savedSelectors;
			savedSelectors = null;
		}
		
		removeTextSelector();
	}

	private void removeTextSelector()
	{
		Iterator<ISelector> it = selectors.iterator();
		
		while(it.hasNext())
		{
			ISelector tmp = it.next();
			
			if( tmp.getSelectorType() == IConstants.SELECTOR_KPM_ID || tmp.getSelectorType() == IConstants.SELECTOR_SHORT_TEXT || tmp.getSelectorType() == IConstants.SELECTOR_PROBLEM_SOLVER)
			{
				it.remove();
			}
		}
	}
}
