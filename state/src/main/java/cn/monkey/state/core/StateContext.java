package cn.monkey.state.core;

import cn.monkey.commons.utils.AttributeKey;

public interface StateContext {

    StateContext EMPTY = new StateContext() {
    };

    default <T> T get(AttributeKey<T> key) {
        throw new UnsupportedOperationException();
    }

    default <T> T set(AttributeKey<T> key, T val) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    default <T extends StateContext> T self(AttributeKey<T> key) {
        return (T) this;
    }
}
