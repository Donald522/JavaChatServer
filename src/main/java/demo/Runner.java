package demo;

import dao.ClientSessionDao;
import dao.DataSourceProvider;
import storage.ClientSessionStorage;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException, SQLException {
        DataSourceProvider dataSourceProvider = new DataSourceProvider("src/main/resources/database.properties");
        ClientSessionDao clientSessionDao;
        ClientSessionStorage storage;
        try {
            clientSessionDao = new ClientSessionDao(dataSourceProvider.getDataSource());
            storage = new ClientSessionStorage(clientSessionDao);
            System.out.println(storage.getUser("anton"));

//            System.out.println(clientSessionDao.storeUser(new User(2, "alex", "superpass")));
        } catch (RefreshFailedException e) {
            //hjhj
        } catch (IllegalArgumentException e) {
            //jhj
        }
    }

}
