package cn.monkey.state.core;

import java.util.Objects;

public class AttributeKey<T> {

    private final String key;

    private AttributeKey(String key) {
        this.key = key;
    }

    public static <T> AttributeKey<T> newInstance(String key) {
        return new AttributeKey<>(key);
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
