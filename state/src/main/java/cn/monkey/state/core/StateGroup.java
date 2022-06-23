package cn.monkey.state.core;

public interface StateGroup<E> {

    String id();

    void addState(State<E> state);

    void setStartState(String stateCode);

    StateContext getStateContext();

    void addEvent(E event);

    void update();

    boolean canClose();

    void close();
}
