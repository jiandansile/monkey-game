package cn.monkey.server;

import cn.monkey.proto.User;
import io.netty.util.AttributeKey;

import java.io.Closeable;
import java.io.IOException;

public interface Session extends Closeable {

    String id();

    <T> T setAttribute(AttributeKey<T> key, T val);

    <T> T getAttribute(AttributeKey<T> key);

    void write(Object data);

    boolean isActive();

    @Override
    void close() throws IOException;
}
