package util.impl;

import util.Factory;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public abstract class AbstractFactory<T> implements Factory<T> {

    protected Map<String, T> objects;

    public AbstractFactory(Map<String, T> objects) {
        this.objects = objects;
    }

    @Override
    public T getObject(String key) {
        return objects.get(key);
    }
}
