package cn.wagentim.work.filter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class MustFixFilter implements ISelector
{

	private final int INDEX_SHORT_TEXT = 7;
	private final int INDEX_RATING = 13;
	private final String M_TAG = "[M]";
	private final String EG = "EG";
	private final String GQ = "GQ";
	private final String A = "A";
	
	@Override
	public Row check(Row row)
	{
		Cell shortText = row.getCell(INDEX_SHORT_TEXT);
		Cell rating = row.getCell(INDEX_RATING);
		
		
		if( null != shortText )
		{
			String content = shortText.getStringCellValue();
			if( content.contains(M_TAG) || content.contains(EG) || content.contains(GQ) )
			{
				return row;
			}
		}
		
		if( null != rating )
		{
			String content = rating.getStringCellValue();
			if( content.contains(A) )
			{
				return row;
			}
		}
		
		return null;
	}
	
}
