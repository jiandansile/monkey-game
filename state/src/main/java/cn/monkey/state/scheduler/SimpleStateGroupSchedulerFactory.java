package cn.monkey.state.scheduler;

import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SimpleStateGroupSchedulerFactory implements StateGroupSchedulerFactory {

    protected final StateGroupSchedulerFactoryConfig stateGroupFactoryConfig;

    protected ThreadFactory threadFactory = Executors.defaultThreadFactory();

    public SimpleStateGroupSchedulerFactory(StateGroupSchedulerFactoryConfig stateGroupFactoryConfig) {
        this.stateGroupFactoryConfig = stateGroupFactoryConfig;
    }

    @Override
    public StateGroupScheduler create(long id) {
        return new SimpleStateGroupScheduler(id,
                this.threadFactory,
                this.stateGroupFactoryConfig.getMaxSize(),
                this.stateGroupFactoryConfig.getUpdateFrequency());
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        Preconditions.checkNotNull(threadFactory);
        this.threadFactory = threadFactory;
    }
}
