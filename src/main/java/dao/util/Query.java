package dao.util;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class Query {

    public static final String LOAD =
            "SELECT * FROM USERS";

    public static final String STORE =
            "INSERT INTO USERS (user_id, user_nickname, user_passhash) " +
                    "VALUES (:user_id, :user_nickname, :user_passhash)";

    private Query() {}

}
