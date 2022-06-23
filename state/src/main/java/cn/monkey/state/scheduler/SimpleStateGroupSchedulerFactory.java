package cn.monkey.state.scheduler;

public class SimpleStateGroupSchedulerFactory implements StateGroupSchedulerFactory {

    protected final StateGroupFactoryConfig stateGroupFactoryConfig;

    public SimpleStateGroupSchedulerFactory(StateGroupFactoryConfig stateGroupFactoryConfig) {
        this.stateGroupFactoryConfig = stateGroupFactoryConfig;
    }

    @Override
    public StateGroupScheduler create(long id) {
        return new SimpleStateGroupScheduler(id,
                this.stateGroupFactoryConfig.getMaxSize(),
                this.stateGroupFactoryConfig.getUpdateFrequency());
    }
}
