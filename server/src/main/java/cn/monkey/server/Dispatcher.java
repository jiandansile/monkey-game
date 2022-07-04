package cn.monkey.server;

import cn.monkey.proto.Command;

public interface Dispatcher<Pkg> {
    void dispatch(Session session, Pkg pkg);
}
