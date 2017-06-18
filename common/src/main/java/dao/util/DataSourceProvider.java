package dao.util;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by Anton Tolkachev.
 * Since 12.01.17
 */

public interface DataSourceProvider {

    DataSource getDataSource() throws IllegalArgumentException, IOException;

}
