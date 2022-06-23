package cn.monkey.state.core;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultStateContext implements StateContext {

    protected final ConcurrentHashMap<AttributeKey<?>, Object> map;

    public DefaultStateContext(){
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(AttributeKey<T> key) {
        return (T) this.map.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T set(AttributeKey<T> key, T val) {
        return (T) this.map.put(key,val);
    }
}
