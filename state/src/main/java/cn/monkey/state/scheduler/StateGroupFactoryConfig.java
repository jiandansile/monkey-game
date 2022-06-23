package cn.monkey.state.scheduler;

public class StateGroupFactoryConfig {
    private static final int DEFAULT_MAX_SIZE = 50;
    private static final long DEFAULT_UPDATE_FREQUENCY = 50L;

    private int maxSize = DEFAULT_MAX_SIZE;
    private long updateFrequency = DEFAULT_UPDATE_FREQUENCY;


    public int getMaxSize() {
        return maxSize;
    }

    public long getUpdateFrequency() {
        return updateFrequency;
    }

    private StateGroupFactoryConfig() {
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {
        private final StateGroupFactoryConfig config;

        Builder() {
            this.config = new StateGroupFactoryConfig();
        }

        public Builder maxSize(int maxSize) {
            this.config.maxSize = maxSize;
            return this;
        }

        public Builder updateFrequency(long updateFrequency) {
            this.config.updateFrequency = updateFrequency;
            return this;
        }

        public StateGroupFactoryConfig build() {
            return this.config;
        }
    }
}
