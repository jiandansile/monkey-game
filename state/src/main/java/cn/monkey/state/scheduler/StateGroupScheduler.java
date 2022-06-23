package cn.monkey.state.scheduler;

import cn.monkey.state.core.StateGroup;

public interface StateGroupScheduler extends Scheduler {
    boolean tryAddStateGroup(StateGroup<?> stateGroup);
}
