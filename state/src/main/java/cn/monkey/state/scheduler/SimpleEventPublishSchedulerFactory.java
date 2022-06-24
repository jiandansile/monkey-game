package cn.monkey.state.scheduler;

public class SimpleEventPublishSchedulerFactory implements EventPublishSchedulerFactory {
    @Override
    public EventPublishScheduler create(long id) {
        return new SimpleEventPublishScheduler(id);
    }
}
