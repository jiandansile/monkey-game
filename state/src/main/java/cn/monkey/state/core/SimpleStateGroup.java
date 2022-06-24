package cn.monkey.state.core;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleStateGroup<E> extends AbstractStateGroup<E> {

    public SimpleStateGroup(String id, StateContext stateContext, boolean canAutoUpdate) {
        super(id, stateContext, canAutoUpdate);
    }

    @Override
    protected BlockingQueue<E> createEventQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Override
    protected Map<String, State<E>> createStateMap() {
        return new ConcurrentHashMap<>();
    }
}
