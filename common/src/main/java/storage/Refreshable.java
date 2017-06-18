package storage;

import javax.security.auth.RefreshFailedException;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public interface Refreshable {

    void refresh() throws RefreshFailedException;

}
