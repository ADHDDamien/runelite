package net.runelite.client.plugins.devtools;

import java.util.HashMap;
import java.util.Map;

public class WidgetSearchMap {

    private final Map<String, Object> baseMap;

    public WidgetSearchMap() {
        baseMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T put(Key<T> key, T value) {
        return (T) baseMap.put(key.getName(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Key<T> key) {
        T value = (T) baseMap.get(key.getName());
        return value;
    }

    public int size() {
        return baseMap.size();
    }

    public void clear() {
        baseMap.clear();
    }

    public boolean containsKey(String key){
        return baseMap.containsKey(key);
    }

    public static class Key<T> {
        private final String keyName;

        public Key(String key) {
            this.keyName = key;
        }

        public String getName() {
            return keyName;
        }
    }
}
