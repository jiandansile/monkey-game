package cn.monkey.state.core;

import java.io.Closeable;

public interface StateGroup<E> extends Closeable {

    String id();

    void addState(State<E> state);

    void setStartState(String stateCode);

    StateContext getStateContext();

    void addEvent(E event);

    void update();

    boolean canClose();

    void close();
}
