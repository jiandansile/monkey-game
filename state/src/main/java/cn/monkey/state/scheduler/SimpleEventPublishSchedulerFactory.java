package cn.monkey.state.scheduler;

import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SimpleEventPublishSchedulerFactory implements EventPublishSchedulerFactory {

    protected ThreadFactory threadFactory = Executors.defaultThreadFactory();

    @Override
    public EventPublishScheduler create(long id) {
        return new SimpleEventPublishScheduler(id, this.threadFactory);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        Preconditions.checkNotNull(threadFactory);
        this.threadFactory = threadFactory;
    }
}
