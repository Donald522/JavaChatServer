package dao;


import dao.util.Query;
import model.Relation;
import model.RelationRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 03.04.17
 */

public class ContactListDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public ContactListDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }

    public boolean storeRequest(RelationRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_1", request.getFirst());
        parameters.put("user_2", request.getSecond());
        parameters.put("message", request.getMessage());
        parameters.put("status", request.getStatus());

        int update = jdbcTemplate.update(Query.STORE_CONTACT_REQUEST, parameters);
        return update == 1;
    }

    public Collection<Relation> loadAllFriends() {
        return jdbcTemplate.query(Query.LOAD_ALL_RELATIONS, new RowMapper<Relation>() {
            @Override
            public Relation mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Relation.Builder()
                        .setFirst(resultSet.getString("user_1"))
                        .setSecond(resultSet.getString("user_2"))
                        .build();
            }
        });
    }

    public Collection<RelationRequest> loadAllRequests() {
        return jdbcTemplate.query(Query.LOAD_ALL_REQUESTS, new RowMapper<RelationRequest>() {
            @Override
            public RelationRequest mapRow(ResultSet resultSet, int i) throws SQLException {
                return new RelationRequest.Builder()
                        .setFirst(resultSet.getString("user_1"))
                        .setSecond(resultSet.getString("user_2"))
                        .setMessage(resultSet.getString("message"))
                        .setStatus(resultSet.getInt("status"))
                        .build();
            }
        });
    }

    public boolean approveRequest(RelationRequest request) {
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("user_1", request.getFirst());
                    parameters.put("user_2", request.getSecond());
                    parameters.put("status", NumberUtils.BYTE_ONE);

                    int update = jdbcTemplate.update(Query.STORE_CONTACT, parameters);
                    if(update != 1) {
                        throw new CannotCreateTransactionException("Error in update");
                    }

                    update = jdbcTemplate.update(Query.UPDATE_REQUEST, parameters);
                    if(update != 1) {
                        throw new CannotCreateTransactionException("Error in update");
                    }
                }
            });
        } catch (TransactionException e) {
            return false;
        }
        return true;
    }

    public boolean rejectRequest(RelationRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_1", request.getFirst());
        parameters.put("user_2", request.getSecond());
        parameters.put("status", NumberUtils.BYTE_MINUS_ONE);

        int update = jdbcTemplate.update(Query.UPDATE_REQUEST, parameters);
        return update == 1;
    }

    public boolean removeContact(Relation relation) {
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("user_1", relation.getFirst());
                    parameters.put("user_2", relation.getSecond());
                    parameters.put("status", NumberUtils.BYTE_MINUS_ONE);

                    int update = jdbcTemplate.update(Query.REMOVE_CONTACT, parameters);
                    if(update != 1) {
                        throw new CannotCreateTransactionException("Error in update");
                    }

                    update = jdbcTemplate.update(Query.UPDATE_REQUEST, parameters);
                    if(update != 1) {
                        throw new CannotCreateTransactionException("Error in update");
                    }
                }
            });
        } catch (TransactionException e) {
            return false;
        }
        return true;
    }
}
