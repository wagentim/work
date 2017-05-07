package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.basicutils.Validator;

public abstract class AbstractSelector implements ISelector
{
	protected List<String> searchContent = new ArrayList<String>();
	
	public void addSearchContent(String content)
	{
		if( Validator.isNullOrEmpty(content) )
		{
			return;
		}
		
		if( !searchContent.contains(content) )
		{
			searchContent.add(content);
		}
	}
	
	public void removeSearchContent(String content)
	{
		if( Validator.isNullOrEmpty(content) )
		{
			return;
		}
		
		if( searchContent.contains(content) )
		{
			searchContent.remove(content);
		}
	}
	
	public List<String> getSearchContent()
	{
		return searchContent;
	}
}
