package cn.monkey.state.core;

public interface State<Event> {

    String code();

    void init() throws Exception;

    void initOnError(Exception e);

    void fireEvent(Event event) throws Exception;

    void fireEventOnError(Event event, Exception e);

    void update(StateInfo stateInfo) throws Exception;

    void updateOnError(StateInfo stateInfo, Exception e);

    String finish() throws Exception;

    String finishOnError(Exception e);
}
