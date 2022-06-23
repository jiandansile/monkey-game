package cn.monkey.state.scheduler;

import cn.monkey.state.scheduler.strategy.WaitingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleEventPublishScheduler extends AbstractScheduler implements EventPublishScheduler {

    protected final BlockingQueue<Runnable> taskQueue;

    protected final WaitingStrategy waitingStrategy;

    public SimpleEventPublishScheduler(long id) {
        super(id);
        this.taskQueue = new LinkedBlockingQueue<>();
        this.waitingStrategy = WaitingStrategy.blocking();
    }

    @Override
    protected Thread newThread() {
        return new Thread(() -> {
            for (; ; ) {
                execute();
                try {
                    this.waitingStrategy.await();
                } catch (InterruptedException e) {
                    log.error("waitingStrategy#await error:\n", e);
                }
            }
        });
    }

    protected final void execute() {
        if (this.taskQueue.isEmpty()) {
            return;
        }
        List<Runnable> list = new ArrayList<>(this.taskQueue.size());
        this.taskQueue.drainTo(list);
        for (Runnable r : list) {
            r.run();
        }
    }

    @Override
    public void publish(Runnable r) {
        if (this.taskQueue.offer(r)) {
            this.waitingStrategy.signalAllWhenBlocking();
            return;
        }
        log.error("id: {} eventQueue is full", this.id());
    }
}
