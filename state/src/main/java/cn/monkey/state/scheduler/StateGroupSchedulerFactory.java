package cn.monkey.state.scheduler;

public interface StateGroupSchedulerFactory extends SchedulerFactory {
    StateGroupScheduler create(long id);
}
