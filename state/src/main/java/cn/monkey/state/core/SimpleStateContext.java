package cn.monkey.state.core;

import cn.monkey.commons.utils.AttributeKey;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleStateContext implements StateContext {

    protected final ConcurrentHashMap<AttributeKey<?>, Object> map;

    public SimpleStateContext(){
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
