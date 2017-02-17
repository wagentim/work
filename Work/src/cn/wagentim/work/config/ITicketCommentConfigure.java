package cn.wagentim.work.config;

public interface ITicketCommentConfigure extends IConfigure
{
	int getCommentColumnIndex();
	int getStatusColumnIndex();
	int getPriorityColumnIndex();
	int getNextStepColumnIndex();
}
