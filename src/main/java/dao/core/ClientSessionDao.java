package dao.core;

import dao.util.Query;
import model.user.Profile;
import model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        return jdbcTemplate.query(Query.LOAD_USER, new RowMapper<User>() {
           public User mapRow(ResultSet resultSet, int i) throws SQLException {
               int id = resultSet.getInt("user_id");
               String name = resultSet.getString("user_nickname");
               String pass = resultSet.getString("user_passhash");
               User user = new User(id, name, pass);

               user.setProfile(Profile.newBuilder()
                       .setGender(getValueOrDefault(resultSet.getString("gender")))
                       .setDateOfBirth(resultSet.getDate("DOB"))
                       .setEmail(getValueOrDefault(resultSet.getString("email")))
                       .setCountry(getValueOrDefault(resultSet.getString("country")))
                       .setCity(getValueOrDefault(resultSet.getString("city")))
                       .setInterests(new HashSet<>(Arrays.asList(getValueOrDefault(resultSet.getString("interests"))
                               .split(","))))
                       .build()
               );
               return user;
           }
       });
    }

    public int storeUser(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getId());
        parameters.put("user_nickname", user.getName());
        parameters.put("user_passhash", user.getPassword());

        return jdbcTemplate.update(Query.STORE_USER, parameters);
    }

    public int storeUserProfile(User user, Profile profile) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", user.getId());
        parameters.put("gender", getValueOrDefault(profile.getGender()));
        parameters.put("dob", profile.getDateOfBirth());
        parameters.put("email", getValueOrDefault(profile.getEmail()));
        parameters.put("country", getValueOrDefault(profile.getCountry()));
        parameters.put("city", getValueOrDefault(profile.getCity()));
        parameters.put("interests", StringUtils.join(profile.getInterests(), ','));

        return jdbcTemplate.update(Query.UPDATE_PROFILE, parameters);
    }

    private String getValueOrDefault(String gender) {
        return StringUtils.isBlank(gender) ? StringUtils.EMPTY : gender;
    }

}
