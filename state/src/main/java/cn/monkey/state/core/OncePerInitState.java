package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public abstract class OncePerInitState<Event> extends AbstractState<Event> {

    protected boolean hasInit = false;

    public OncePerInitState(Timer timer, StateGroup<Event> stateGroup) {
        super(timer, stateGroup);
    }


    @Override
    public void init() throws Exception {
        if (this.hasInit) {
            return;
        }
        this.onInit();
        this.hasInit = true;
    }

    protected abstract void onInit();
}
