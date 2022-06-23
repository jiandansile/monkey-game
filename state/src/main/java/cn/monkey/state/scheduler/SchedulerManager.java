package cn.monkey.state.scheduler;

public interface SchedulerManager<Event> {
    void addEvent(String groupId,Event event);
}
