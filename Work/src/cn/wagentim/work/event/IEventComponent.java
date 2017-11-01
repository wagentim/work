package cn.wagentim.work.event;

public interface IEventComponent
{
	void receiveEvent(IEvent event);
	void sentEvent(IEvent event);
}
