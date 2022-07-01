package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;

import java.util.concurrent.ThreadFactory;

public abstract class EventLoopScheduler extends AbstractScheduler {

    protected final WaitingStrategy waitingStrategy;

    protected final ThreadFactory threadFactory;

    public EventLoopScheduler(long id,
                              WaitingStrategy waitingStrategy,
                              ThreadFactory threadFactory) {
        super(id);
        this.waitingStrategy = waitingStrategy;
        this.threadFactory = threadFactory;
    }

    @Override
    protected Thread newThread() {
        return this.threadFactory.newThread(() -> {
            for (; ; ) {
                try {
                    this.waitingStrategy.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                this.execute();
            }
        });
    }

    protected abstract void execute();
}
