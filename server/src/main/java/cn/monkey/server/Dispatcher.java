package cn.monkey.server;

import cn.monkey.proto.Command;

public interface Dispatcher {
    void dispatch(Session session, Command.Package pkg);
}
