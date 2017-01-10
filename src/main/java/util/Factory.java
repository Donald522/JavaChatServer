package util;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public interface Factory<T> {

    T getObject(String key);

}
