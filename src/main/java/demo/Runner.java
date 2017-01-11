package demo;

import dao.core.ClientSessionDao;
import dao.util.DataSourceProvider;
import dao.util.MysqlDataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import model.command.Command;
import model.command.factory.CommandFactory;
import model.command.impl.SignUpCommand;
import storage.ClientSessionStorage;
import util.Factory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException, SQLException {
        DataSourceProvider dataSourceProvider = new MysqlDataSourceProvider("src/main/resources/database.properties");
        ClientSessionDao clientSessionDao;
        ClientSessionStorage storage;
        ClientMessageParser parser;
        Factory<?> factory = new CommandFactory(new HashMap<String, Command>(){{
            put("signup", new SignUpCommand());
        }});
//        System.out.println(factory.getObject("signup").getClass());
        parser = new ClientMessageParserImpl(factory);
        parser.parseInput("signup").handle();

//        try {
//            clientSessionDao = new ClientSessionDao(mysqlDataSourceProvider.getDataSource());
//            storage = new ClientSessionStorage(clientSessionDao);
////            System.out.println(storage.getUser("anton"));
//            parser = new ClientMessageParserImpl();
//            parser.parseInput("").handle();
//
//
////            System.out.println(clientSessionDao.storeUser(new User(2, "alex", "superpass")));
//        } catch (RefreshFailedException e) {
//            //hjhj
//        } catch (IllegalArgumentException e) {
//            //jhj
//        }
    }

}
