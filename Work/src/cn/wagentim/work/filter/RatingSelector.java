package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.wagentim.basicutils.Validator;
import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class RatingSelector extends AbstractSelector
{
	
	private List<String> selectedRatings = Collections.EMPTY_LIST;
	
	public RatingSelector()
	{
		selectedRatings = new ArrayList<String>();
	}
	
	public static final String[] RATING_OPTIONS = {
		IConstants.STRING_RATING_A,
		IConstants.STRING_RATING_B,
		IConstants.STRING_RATING_C,
		IConstants.STRING_RATING_D
	};

	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		List<TicketEntity> result = new ArrayList<TicketEntity>();

		if( !Validator.isNullOrEmpty(selectedRatings) )
		{
			for( TicketEntity t : list )
			{
				if( null == t )
				{
					continue;
				}

				for( String rating : selectedRatings )
				{
					if( t.getRating().equals(rating) )
					{
						result.add(t);
					}
				}
			}
		}		
		return result;
	}

	@Override
	public void setSearchContent(List<String> content)
	{
		this.selectedRatings = content;
	}

	@Override
	public void setSearchContent(String content)
	{
		this.selectedRatings.clear();
		
		if( !Validator.isNullOrEmpty(content) )
		{
			this.selectedRatings.add(content);
		}
	}

	@Override
	public List<String> getSearchContent()
	{
		return selectedRatings;
	}

}
