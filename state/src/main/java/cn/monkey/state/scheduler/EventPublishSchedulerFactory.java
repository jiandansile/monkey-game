package cn.monkey.state.scheduler;

import java.util.concurrent.ThreadFactory;

public interface EventPublishSchedulerFactory extends SchedulerFactory {
    EventPublishScheduler create(long id);

    void setThreadFactory(ThreadFactory threadFactory);
}
