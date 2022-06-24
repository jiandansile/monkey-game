package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public class SimpleStateGroupFactory<E> implements StateGroupFactory<E> {

    protected boolean canAutoUpdate = true;

    protected final Timer timer;

    public SimpleStateGroupFactory(Timer timer) {
        this.timer = timer;
    }

    public void setCanAutoUpdate(boolean canAutoUpdate) {
        this.canAutoUpdate = canAutoUpdate;
    }

    @Override
    public StateGroup<E> create(String id) {
        StateGroup<E> stateGroup = new SimpleStateGroup<>(id, StateContext.EMPTY, this.canAutoUpdate);
        State<E> state = new EmptyState<>(this.timer, stateGroup);
        stateGroup.addState(state);
        stateGroup.setStartState(EmptyState.CODE);
        return stateGroup;
    }
}
