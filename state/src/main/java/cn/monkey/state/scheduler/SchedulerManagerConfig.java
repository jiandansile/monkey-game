package cn.monkey.state.scheduler;

public class SchedulerManagerConfig {
    private static final int DEFAULT_STATE_GROUP_SCHEDULER_SIZE = 10;
    private static final int DEFAULT_EVENT_PUBLISH_SCHEDULER_SIZE = 1 << 3;

    private int stateGroupSchedulerSize = DEFAULT_STATE_GROUP_SCHEDULER_SIZE;
    private int eventPublisherSchedulerSize = DEFAULT_EVENT_PUBLISH_SCHEDULER_SIZE;

    public int getEventPublisherSchedulerSize() {
        return eventPublisherSchedulerSize;
    }

    public int getStateGroupSchedulerSize() {
        return stateGroupSchedulerSize;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {
        private final SchedulerManagerConfig config;

        Builder() {
            config = new SchedulerManagerConfig();
        }

        public Builder stateGroupSchedulerSize(int stateGroupSchedulerSize) {
            this.config.stateGroupSchedulerSize = stateGroupSchedulerSize;
            return this;
        }

        public Builder eventPublisherSchedulerSize(int eventPublisherSchedulerSize) {
            this.config.eventPublisherSchedulerSize = eventPublisherSchedulerSize;
            return this;
        }

        public SchedulerManagerConfig build() {
            return this.config;
        }
    }
}