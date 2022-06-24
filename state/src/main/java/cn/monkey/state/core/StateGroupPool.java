package cn.monkey.state.core;

public interface StateGroupPool<E> {
    class FetchStateGroup<E> {
        private final boolean isNew;
        private final StateGroup<E> stateGroup;

        public FetchStateGroup(boolean isNew, StateGroup<E> stateGroup) {
            this.isNew = isNew;
            this.stateGroup = stateGroup;
        }

        public boolean isNew() {
            return isNew;
        }

        public StateGroup<E> getStateGroup() {
            return stateGroup;
        }
    }

    FetchStateGroup<E> findOrCreate(String id);
}
