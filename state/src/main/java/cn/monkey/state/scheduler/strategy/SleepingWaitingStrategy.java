package cn.monkey.state.scheduler.strategy;

class SleepingWaitingStrategy implements WaitingStrategy {

    private final long waitTime;

    private final Object LOCK;

    SleepingWaitingStrategy(long waitTime) {
        this.waitTime = waitTime;
        this.LOCK = new Object();
    }

    @Override
    public void await() {
        synchronized (this.LOCK) {
            try {
                this.LOCK.wait(this.waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
