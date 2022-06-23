package cn.monkey.server;

import cn.monkey.proto.Command;

public interface Filter {
    boolean filter(Session session, Command.Package pkg);
}
