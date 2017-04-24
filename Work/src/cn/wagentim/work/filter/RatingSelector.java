package cn.wagentim.work.filter;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.entities.work.TicketEntity;
import cn.wagentim.work.config.IConstants;

public class RatingSelector extends AbstractSelector
{

	public static final String[] RATING_OPTIONS =
	{ IConstants.STRING_RATING_A, IConstants.STRING_RATING_B, IConstants.STRING_RATING_C, IConstants.STRING_RATING_D };

	@Override
	public List<TicketEntity> check(List<TicketEntity> list)
	{
		List<TicketEntity> result = new ArrayList<TicketEntity>();

		for (TicketEntity t : list)
		{
			if (null == t)
			{
				continue;
			}

			for (String rating : searchContent)
			{
				if (t.getRating().equals(rating))
				{
					result.add(t);
				}
			}
		}
		
		return result;
	}

	@Override
	public int getSelectorType()
	{
		return IConstants.SELECTOR_RATING;
	}

}
