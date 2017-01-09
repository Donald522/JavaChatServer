package dao;

import model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 07.01.17
 */

public class ClientSessionDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ClientSessionDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<User> loadAllUsers() {
        return jdbcTemplate.query(Query.LOAD, new RowMapper<User>() {
           public User mapRow(ResultSet resultSet, int i) throws SQLException {
               return new User(
                       resultSet.getInt("user_id"),
                       resultSet.getString("user_nickname"),
                       resultSet.getString("user_passhash")
               );
           }
       });
    }

    public int storeUser(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getId());
        parameters.put("user_nickname", user.getName());
        parameters.put("user_passhash", user.getPassword());

        return jdbcTemplate.update(Query.STORE, parameters);
    }

}
