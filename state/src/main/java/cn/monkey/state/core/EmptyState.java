package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public class EmptyState<Event> extends AbstractState<Event> {

    public static final String CODE = "empty";

    public EmptyState(Timer timer, StateGroup<Event> stateGroup) {
        super(timer, stateGroup);
    }

    @Override
    public String code() {
        return CODE;
    }

    @Override
    public String finish() throws Exception {
        return null;
    }
}
