package cn.monkey.state.core;

public interface StateGroupFactory<E> {
    StateGroup<E> create(String id);
}
