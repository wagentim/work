package cn.wagentim.work.filter;

public abstract class AbstractSelector implements ISelector
{
	
	private boolean isExclusive = false;
	
	@Override
	public boolean isExclusive()
	{
		return isExclusive;
	}

	@Override
	public void setExclusive(boolean isExclusive)
	{
		this.isExclusive = isExclusive;
	}

}
