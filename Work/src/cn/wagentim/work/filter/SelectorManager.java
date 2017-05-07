package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.work.config.IConstants;

public class SelectorManager 
{
	private List<ISelector> selectors = new ArrayList<ISelector>();
	private List<ISelector> savedSelectors = null;
	
	public final List<ISelector> getSelectors()
	{
		return selectors;
	}
	
	public void clearAllSelectors()
	{
		selectors.clear();
	}
	
	public void addNewSelector(ISelector selector)
	{
		if( !Validator.isNull(selector) )
		{
			selectors.add(selector);
		}
	}
	
	public void addNewSelector(int selector)
	{
		addNewSelector(createNewSelector(selector));
	}
	
	public ISelector getSelector(int selector)
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
		
		if( null == result )
		{
			result = createNewSelector(selector);
			addNewSelector(result);
		}
		
		return result;
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
	
	public void removeTextSelector()
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
