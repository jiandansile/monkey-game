package cn.monkey.state.scheduler;

import java.util.concurrent.ThreadFactory;

public interface StateGroupSchedulerFactory extends SchedulerFactory {
    StateGroupScheduler create(long id);

    void setThreadFactory(ThreadFactory threadFactory);
}
