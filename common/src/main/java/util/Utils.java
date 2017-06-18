package util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;

/**
 * Created by Anton Tolkachev.
 * Since 25.02.17
 */

public class Utils {

    public static Date getDate(String str) {
        if(StringUtils.isBlank(str)) {
            return null;
        }
        return Date.valueOf(str);
    }

}
