package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class AbstractState<Event> implements State<Event> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final Timer timer;

    protected final StateGroup<Event> stateGroup;

    public AbstractState(Timer timer,
                         StateGroup<Event> stateGroup) {
        Objects.requireNonNull(timer, "timer can not be null");
        Objects.requireNonNull(stateGroup, "stateGroup can not be null");
        this.timer = timer;
        this.stateGroup = stateGroup;
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public StateGroup<Event> getStateGroup() {
        return this.stateGroup;
    }

    @Override
    public void initOnError(Exception e) {
        log.error("stateGroup: {} state: {} init error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public void fireEvent(Event event) throws Exception {

    }

    @Override
    public void fireEventOnError(Event event, Exception e) {
        log.error("stateGroup: {} state: {} fireEvent error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public void update(StateInfo stateInfo) throws Exception {

    }

    @Override
    public void updateOnError(StateInfo stateInfo, Exception e) {
        log.error("stateGroup: {} state: {} update error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public String finishOnError(Exception e) {
        log.error("stateGroup: {} state: {} finish error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
        return null;
    }
}
