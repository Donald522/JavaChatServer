package dao.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Anton Tolkachev.
 * Since 12.01.17
 */

public abstract class AbstractDataSourceProvider implements DataSourceProvider {

    private static final Logger logger = LogManager.getLogger(MysqlDataSourceProvider.class);

    protected String path;

    public AbstractDataSourceProvider() {}

    public AbstractDataSourceProvider(String path) {
        this.path = path;
    }

    protected String checkValidity(String str) throws IllegalArgumentException {
        if(StringUtils.isBlank(str)) {
            logger.info("Property is broken or not found");
            throw new IllegalArgumentException("Property is broken or not found");
        }
        return str;
    }

}
