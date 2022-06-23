package cn.monkey.state.scheduler;

public interface EventPublishSchedulerFactory extends SchedulerFactory {
    EventPublishScheduler create(long id);
}
