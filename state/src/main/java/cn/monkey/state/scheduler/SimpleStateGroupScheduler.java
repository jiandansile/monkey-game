package cn.monkey.state.scheduler;

import cn.monkey.commons.bean.Countable;
import cn.monkey.commons.bean.Refreshable;
import cn.monkey.state.core.StateGroup;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class SimpleStateGroupScheduler extends AbstractScheduler implements StateGroupScheduler, Refreshable, Countable {

    protected volatile ConcurrentHashMap<String, StateGroup<?>> stateGroupMap;

    protected volatile StateGroup<?> currentAddStateGroup;

    protected final int maxSize;

    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<SimpleStateGroupScheduler, StateGroup> STATE_GROUP_UPDATER
            = AtomicReferenceFieldUpdater.newUpdater(SimpleStateGroupScheduler.class, StateGroup.class, "currentAddStateGroup");

    protected SimpleStateGroupScheduler(long id,
                                        int maxSize) {
        super(id);
        this.maxSize = maxSize;
        this.stateGroupMap = new ConcurrentHashMap<>();
    }

    @Override
    protected Thread newThread() {
        return new Thread(() -> {
            for (; ; ) {
                this.execute();
            }
        });
    }

    protected final void execute() {
        for (StateGroup<?> stateGroup : this.stateGroupMap.values()) {
            try {
                stateGroup.update();
            } catch (Exception e) {
                log.error("stateGroup: {} update error", stateGroup.id());
            }
        }
    }

    @Override
    public boolean tryAddStateGroup(StateGroup<?> stateGroup) {
        if (!STATE_GROUP_UPDATER.compareAndSet(this, null, stateGroup)) {
            return false;
        }
        final ConcurrentHashMap<String, StateGroup<?>> stateGroupMap = this.stateGroupMap;
        stateGroupMap.put(this.currentAddStateGroup.id(), this.currentAddStateGroup);
        this.stateGroupMap = stateGroupMap;
        this.currentAddStateGroup = null;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.currentAddStateGroup == null && this.stateGroupMap.isEmpty();
    }

    @Override
    public int size() {
        return (this.currentAddStateGroup == null? 0:1) + this.stateGroupMap.size();
    }

    @Override
    public boolean isFull() {
        return this.size() >=this.maxSize;
    }

    @Override
    public void refresh() {
        final ConcurrentHashMap<String, StateGroup<?>> stateGroupMap = new ConcurrentHashMap<>();
        for(StateGroup<?> stateGroup: this.stateGroupMap.values()){
            if(stateGroup.canClose()){
               continue;
            }
            stateGroupMap.put(stateGroup.id(),stateGroup);
        }
        this.stateGroupMap = stateGroupMap;
    }
}
