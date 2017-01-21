package network;

import dao.core.ClientSessionDao;
import dao.util.DataSourceProvider;
import dao.util.MysqlDataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import model.command.Command;
import model.command.factory.CommandFactory;
import model.command.impl.DefaultCommand;
import model.command.impl.SignUpCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import service.impl.ClientSessionServiceImpl;
import storage.ClientSessionStorage;
import util.Factory;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);

    private static final int DEFAULT_PORT = 7788;
    private static final String PATH = "src/main/resources/database.properties";

    private ServerSocket serverSocket;
    private int port;

    private DataSourceProvider dataSourceProvider;
    private ClientSessionDao dao;
    private ClientSessionStorage storage;
    private SocketProvider socketProvider;
    private final ClientMessageParser parser;
    private ClientSessionService service;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(int port) throws IOException, RefreshFailedException {
        this.port = port;
        serverSocket = new ServerSocket(port);

        dataSourceProvider = new MysqlDataSourceProvider(PATH);
        dao = new ClientSessionDao(dataSourceProvider.getDataSource());
        storage = new ClientSessionStorage(dao);
        socketProvider = new SocketProvider();
        service = new ClientSessionServiceImpl(dao, storage, socketProvider);
        Factory<?> factory = new CommandFactory(new HashMap<String, Command>(){{
            put("signup", new SignUpCommand().withService(service));
        }}).withDefaultValue(new DefaultCommand().withService(service));

        parser = new ClientMessageParserImpl(factory);
    }

    public Server() throws IOException, RefreshFailedException {
        this(DEFAULT_PORT);
    }

    public void start() throws IOException {
        logger.info("Server started");
        while (!serverSocket.isClosed()) {
            final Socket client = serverSocket.accept();
            executorService.execute(() -> {
                socketProvider.setSocket(client);
                new ClientSession(client, parser).run();
            });
        }
        executorService.shutdown();
    }


}