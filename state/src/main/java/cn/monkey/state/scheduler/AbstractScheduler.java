package cn.monkey.state.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractScheduler implements Scheduler {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private final long id;

    protected final Thread t;

    protected volatile boolean isStarted;

    public AbstractScheduler(long id) {
        this.id = id;
        this.t = this.newThread();
    }

    protected abstract Thread newThread();

    @Override
    public long id() {
        return this.id;
    }

    @Override
    public void start() {
        if (this.isStarted && !this.t.isInterrupted()) {
            log.info("{}: {} is start", this.getClass(), this.id());
            this.t.start();
            this.isStarted = true;
        }
    }

    @Override
    public boolean isStart() {
        return this.isStarted;
    }

    @Override
    public void stop() {
        if (!this.t.isInterrupted()) {
            this.t.interrupt();
            this.isStarted = false;
        }
    }
}
