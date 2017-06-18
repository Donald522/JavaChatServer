package dao.util;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public final class Query {

    public static final String LOAD_USER =
            "SELECT * FROM USERS";

    public static final String STORE_USER =
            "INSERT INTO USERS (user_id, user_nickname, user_passhash) " +
                    "VALUES (:user_id, :user_nickname, :user_passhash)";

    public static final String UPDATE_PROFILE =
            "UPDATE USERS  " +
            "SET " +
                    "gender = :gender, " +
                    "DOB = :dob, " +
                    "email = :email, " +
                    "country = :country, " +
                    "city = :city, " +
                    "interests = :interests " +
            "WHERE" +
                    "user_id = :id";

    public static final String STORE_CONTACT_REQUEST =
            "INSERT INTO FRIEND_REQUESTS (user_1, user_2, message, status)" +
                    "VALUES (:user_1, :user_2, :message, :status)";

    public static final String LOAD_ALL_RELATIONS =
            "SELECT * FROM FRIENDS";

    public static final String LOAD_ALL_REQUESTS =
            "SELECT * FROM FRIEND_REQUESTS" +
            "WHERE" +
                    "1 = 1" +
                    "AND status = 0";

    public static final String STORE_CONTACT =
            "INSERT INTO FRIENDS (user_1, user_2)" +
                    "VALUES (:user_1, :user_2)";

    public static final String UPDATE_REQUEST =
            "UPDATE FRIEND_REQUESTS    " +
            "SET" +
                    "status = :status" +
            "WHERE" +
                    "1 = 1" +
                    "AND user_1 = :user_1" +
                    "AND user_2 = :user_2";

    public static final String REMOVE_CONTACT =
            "DELETE FROM FRIENDS" +
            "WHERE" +
                    "1 = 1" +
                    "AND user_1 = :user_1" +
                    "AND user_2 = :user_2";

    private Query() {
        throw new UnsupportedOperationException("Instantiating of utility class is prohibited");
    }

}
