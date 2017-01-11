package dao.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class MysqlDataSourceProvider extends AbstractDataSourceProvider {

    public MysqlDataSourceProvider(String path) {
        super(path);
    }

    public DataSource getDataSource() throws IllegalArgumentException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(path)));

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(checkValidity(properties.getProperty("db.url")));
        dataSource.setUser(checkValidity(properties.getProperty("db.username")));
        dataSource.setPassword(checkValidity(properties.getProperty("db.password")));

        return dataSource;
    }

}
