package dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class DataSourceProvider {

    private static final Logger logger = LogManager.getLogger(DataSourceProvider.class);

    private String path;

    public DataSourceProvider(String path) {
        this.path = path;
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

    private String checkValidity(String str) throws IllegalArgumentException {
        if(StringUtils.isBlank(str)) {
            logger.error("Property is broken or not found.");
            throw new IllegalArgumentException("Property is broken or not found.");
        }
        return str;
    }

}
