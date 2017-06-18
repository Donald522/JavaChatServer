package util;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public interface Factory<T> {

    T getObject(Map<JsonNodes, ?> nodesMap);

}
