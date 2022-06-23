package cn.monkey.state.scheduler;

public interface Scheduler {

    long id();

    void start();

    boolean isStart();

    void stop();
}
