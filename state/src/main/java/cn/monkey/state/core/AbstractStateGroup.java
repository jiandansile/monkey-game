package cn.monkey.state.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractStateGroup<E> implements StateGroup<E> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String id;

    private final StateContext stateContext;

    protected final Map<String, State<E>> stateMap;

    protected final BlockingQueue<E> eventQueue;

    protected final boolean canAutoUpdate;

    protected State<E> currentState;

    public AbstractStateGroup(String id,
                              StateContext stateContext,
                              boolean canAutoUpdate) {
        this.id = id;
        this.stateContext = stateContext;
        this.canAutoUpdate = canAutoUpdate;
        this.stateMap = this.createStateMap();
        this.eventQueue = this.createEventQueue();
    }

    protected abstract BlockingQueue<E> createEventQueue();

    protected abstract Map<String, State<E>> createStateMap();

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public void addState(State<E> state) {
        this.stateMap.put(state.code(), state);
    }

    @Override
    public void setStartState(String stateCode) {
        this.currentState = Objects.requireNonNull(this.stateMap.get(stateCode));
    }

    @Override
    public StateContext getStateContext() {
        return this.stateContext;
    }

    @Override
    public void addEvent(E event) {
        if (!this.eventQueue.offer(event)) {
            log.error("group: {} eventQueue is full", this.id());
        }
    }

    @Override
    public void update() {
        if (null == this.currentState) {
            return;
        }
        try {
            this.currentState.init();
        } catch (Exception e) {
            this.currentState.initOnError(e);
        }
        E event = this.eventQueue.poll();
        if (null == event) {
            if (!canAutoUpdate) {
                return;
            }
            this.updateAndTrySwitch2NextState();
            return;
        }
        try {
            this.currentState.fireEvent(event);
        } catch (Exception e) {
            this.currentState.fireEventOnError(event, e);
        }
        this.updateAndTrySwitch2NextState();
    }

    protected void updateAndTrySwitch2NextState() {
        StateInfo stateInfo = new StateInfo();
        try {
            this.currentState.update(stateInfo);
        } catch (Exception e) {
            this.currentState.updateOnError(stateInfo, e);
        }
        if (!stateInfo.isFinish) {
            return;
        }
        String nextStateCode;
        try {
            nextStateCode = this.currentState.finish();
        } catch (Exception e) {
            nextStateCode = this.currentState.finishOnError(e);
        }
        if (null == nextStateCode) {
            this.currentState = null;
            return;
        }
        this.currentState = this.stateMap.get(nextStateCode);
    }

    @Override
    public boolean canClose() {
        return this.currentState == null;
    }

    @Override
    public void close() {

    }
}
