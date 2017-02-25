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
            "WHERE user_id = :id";

    private Query() {
        throw new UnsupportedOperationException();
    }

}
