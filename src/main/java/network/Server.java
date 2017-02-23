package network;

import dao.core.ClientSessionDao;
import dao.util.DataSourceProvider;
import dao.util.MysqlDataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import model.command.Command;
import model.command.factory.CommandFactory;
import model.command.impl.*;
import network.receiver.Receiver;
import network.receiver.impl.SimpleReceiver;
import network.sender.Sender;
import network.sender.impl.SimpleSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import service.DialogService;
import service.MonitoringService;
import service.StreamProvider;
import service.impl.ClientSessionServiceImpl;
import service.impl.DialogServiceImpl;
import service.impl.MonitoringServiceImpl;
import service.impl.SocketStreamProvider;
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
    private DialogService dialogService;
    private SocketProvider socketProvider;
    private final StreamProvider streamProvider;
    private Sender sender;
    private Receiver receiver;
    private final ClientMessageParser parser;
    private ClientSessionService service;
    private Factory<?> factory;

    private MonitoringService monitoringService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(int port) throws IOException, RefreshFailedException {

        this.port = port;
        serverSocket = new ServerSocket(port);
        dataSourceProvider = new MysqlDataSourceProvider(PATH);
        dao = new ClientSessionDao(dataSourceProvider.getDataSource());
        storage = new ClientSessionStorage(dao);
        streamProvider = new SocketStreamProvider();
        parser = new ClientMessageParserImpl();
        sender = new SimpleSender(parser, streamProvider);
        dialogService = new DialogServiceImpl(sender);
        service = new ClientSessionServiceImpl(dao, storage, dialogService, socketProvider);
        factory = new CommandFactory(new HashMap<String, Command>(){{
            put("signup", new SignUpCommand().withService(service));
            put("signin", new SignInCommand().withService(service));
            put("newdlg", new CreateDialogCommand().withService(service));
            put("sendmsg", new SendMessageCommand().withService(service));
        }}).withDefaultValue(new DefaultCommand().withService(service));

        receiver = new SimpleReceiver(streamProvider, parser, factory);
        socketProvider = new SocketProvider(sender);

        monitoringService = new MonitoringServiceImpl(storage);

    }

    public Server() throws IOException, RefreshFailedException {
        this(DEFAULT_PORT);
    }

    public void start() throws IOException {
        dialogService.start();
        monitoringService.start();
        logger.info("Server started");
        while (!serverSocket.isClosed()) {
            final Socket client = serverSocket.accept();
            client.setSoTimeout(60_000);
            executorService.execute(() -> {
                socketProvider.setSocket(client);
                new ClientSession(client, sender, receiver).run();
            });
        }
        logger.info("Server is shutting down");
        executorService.shutdown();
    }


}
