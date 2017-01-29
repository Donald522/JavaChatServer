package demo;

import dao.core.ClientSessionDao;
import dao.util.DataSourceProvider;
import dao.util.MysqlDataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import model.command.Command;
import model.command.factory.CommandFactory;
import model.command.impl.DefaultCommand;
import model.command.impl.SignInCommand;
import model.command.impl.SignUpCommand;
import service.ClientSessionService;
import service.DialogService;
import service.impl.ClientSessionServiceImpl;
import service.impl.DialogServiceImpl;
import storage.ClientSessionStorage;
import util.Factory;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException, SQLException, RefreshFailedException {

        final String json = "{\"command\":\"signup\", " +
                "\"username\":\"AntonTolkachev\", " +
                "\"password\":\"pass123\"}";

        final String path = "src/main/resources/database.properties";

        DataSourceProvider dataSourceProvider;
        ClientSessionDao dao;
        ClientSessionStorage storage;
        DialogService dialogService;
        ClientMessageParser parser;
        ClientSessionService service;

        dataSourceProvider = new MysqlDataSourceProvider(path);
        dao = new ClientSessionDao(dataSourceProvider.getDataSource());
        storage = new ClientSessionStorage(dao);
        dialogService = new DialogServiceImpl();
        service = new ClientSessionServiceImpl(dao, storage, dialogService);
        Factory<?> factory = new CommandFactory(new HashMap<String, Command>(){{
            put("signup", new SignUpCommand().withService(service));
            put("signin", new SignInCommand().withService(service));
        }}).withDefaultValue(new DefaultCommand().withService(service));

        parser = new ClientMessageParserImpl(factory);

        parser.parseInput("{ \"users\" : [] }");
//        try {
//            Command<?> command = parser.parseInput(json);
//            command.handle();
//        } catch (RuntimeException e) {
//            System.out.println("Name is already used. Please try another.");
//        }

    }

}
