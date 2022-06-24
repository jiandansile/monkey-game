package cn.monkey.commons.utils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class AttributeKey<T> {

    private static final ConcurrentHashMap<String, AttributeKey<?>> constantMap = new ConcurrentHashMap<>();

    private final String key;

    private AttributeKey(String key) {
        this.key = key;
    }

    @SuppressWarnings("unchecked")
    public static <T> AttributeKey<T> newInstance(String key) {
        return (AttributeKey<T>) constantMap.computeIfAbsent(key, AttributeKey::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeKey<?> that = (AttributeKey<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
