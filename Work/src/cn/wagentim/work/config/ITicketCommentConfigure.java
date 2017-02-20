package cn.wagentim.work.config;

public interface ITicketCommentConfigure extends IImportConfigure
{
	int getCommentColumnIndex();
	int getStatusColumnIndex();
	int getPriorityColumnIndex();
	int getNextStepColumnIndex();
}
