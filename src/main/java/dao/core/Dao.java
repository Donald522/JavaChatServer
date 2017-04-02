package dao.core;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 02.04.17
 */

public interface Dao<T> {

    Collection<T> load(Map<String, Object> params);

    Collection<T> loadAll();

    int store(T data);

    int storeAll(Collection<T> data);

    int update(T data);

    int updateAll(Collection<T> data);

}
