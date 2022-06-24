package cn.monkey.state.scheduler;

public interface SchedulerFactory {
    Scheduler create(long id);
}
