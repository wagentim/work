package cn.wagentim.work.filter;

import cn.wagentim.basicutils.Validator;

public abstract class TextSelector extends AbstractSelector
{
	
	public void addSearchContent(String content)
	{
		if( Validator.isNullOrEmpty(content) )
		{
			return;
		}
		
		searchContent.clear();
		searchContent.add(content);
	}
}
