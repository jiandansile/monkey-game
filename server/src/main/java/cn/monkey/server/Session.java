package cn.monkey.server;

import io.netty.util.AttributeKey;

import java.io.Closeable;
import java.io.IOException;

public interface Session extends Closeable {

    String id();

    <T> T setAttribute(AttributeKey<T> key, T val);

    void write(Object data);

    boolean isAlive();

    @Override
    void close() throws IOException;
}
