package by.du.web;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
    private final Map<Class<?>, Object> map = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> type) {
        return (T) map.get(type);
    }

    public <T> void put(Class<?> type, T obj) {
        map.put(type, obj);
    }
}
