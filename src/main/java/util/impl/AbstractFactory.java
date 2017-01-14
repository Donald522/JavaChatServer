package util.impl;

import util.Factory;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public abstract class AbstractFactory<T> implements Factory<T> {

    protected Map<String, T> objects;

    protected T defaultValue;

    public AbstractFactory(Map<String, T> objects) {
        this.objects = objects;
    }

    public AbstractFactory(Map<String, T> objects, T defaultValue) {
        this.objects = objects;
        this.defaultValue = defaultValue;
    }

    @Override
    public T getObject(String key) {
        T result = objects.get(key);
        return result != null ? result : defaultValue;
    }

    public Factory<?> withDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setObjects(Map<String, T> objects) {
        this.objects = objects;
    }
}
