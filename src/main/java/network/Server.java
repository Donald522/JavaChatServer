package network;

import com.google.common.collect.ImmutableMap;
import dao.core.impl.ClientSessionDao;
import dao.core.impl.ContactListDao;
import dao.util.DataSourceProvider;
import dao.util.MysqlDataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import model.command.Command;
import model.command.factory.CommandFactory;
import model.command.impl.ApproveRequestCommand;
import model.command.impl.ContactRequestCommand;
import model.command.impl.CreateDialogCommand;
import model.command.impl.DefaultCommand;
import model.command.impl.GetContactsCommand;
import model.command.impl.GetInboundRequestsCommand;
import model.command.impl.GetOutboundRequestsCommand;
import model.command.impl.RejectRequestCommand;
import model.command.impl.RemoveContactCommand;
import model.command.impl.SendMessageCommand;
import model.command.impl.SignInCommand;
import model.command.impl.SignOutCommand;
import model.command.impl.SignUpCommand;
import model.command.impl.UpdateProfileCommand;
import network.receiver.Receiver;
import network.receiver.impl.SimpleReceiver;
import network.sender.Sender;
import network.sender.impl.SimpleSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import service.ContactRequestSender;
import service.ContactService;
import service.DialogService;
import service.MonitoringService;
import service.StreamProvider;
import service.impl.ClientSessionServiceImpl;
import service.impl.ContactRequestSenderImpl;
import service.impl.ContactServiceImpl;
import service.impl.DialogServiceImpl;
import service.impl.MonitoringServiceImpl;
import service.impl.SocketStreamProvider;
import storage.ClientSessionStorage;
import storage.ContactListStorage;
import util.Factory;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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
    private ContactListDao contactListDao;
    private ContactListStorage contactListStorage;
    private ContactRequestSender contactRequestSender;
    private ContactService contactService;
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
        socketProvider = new SocketProvider(sender);
        dialogService = new DialogServiceImpl(sender);
        contactListDao = new ContactListDao(dataSourceProvider.getDataSource());
        contactListStorage = new ContactListStorage(contactListDao);
        contactRequestSender = new ContactRequestSenderImpl(sender);
        contactService = new ContactServiceImpl(storage, contactListStorage, contactRequestSender);
        service = new ClientSessionServiceImpl(storage, dialogService, contactService, socketProvider);
        factory = initializeFactory();

        receiver = new SimpleReceiver(streamProvider, parser, factory);

        monitoringService = new MonitoringServiceImpl(storage);

    }

    private Factory<?> initializeFactory() {
        Map<String, Command> params = ImmutableMap.<String, Command>builder()
                .put("signup", new SignUpCommand().withService(service))
                .put("signin", new SignInCommand().withService(service))
                .put("signout", new SignOutCommand().withService(service))
                .put("newdlg", new CreateDialogCommand().withService(service))
                .put("sendmsg", new SendMessageCommand().withService(service))
                .put("updpfl", new UpdateProfileCommand().withService(service))
                .put("frdrq", new ContactRequestCommand().withService(service))
                .put("aprvrq", new ApproveRequestCommand().withService(service))
                .put("rjctrq", new RejectRequestCommand().withService(service))
                .put("rmfrnd", new RemoveContactCommand().withService(service))
                .put("getfrnds", new GetContactsCommand().withService(service))
                .put("getinrqs", new GetInboundRequestsCommand().withService(service))
                .put("getoutrqs", new GetOutboundRequestsCommand().withService(service))
                .build();
        return new CommandFactory(params).withDefaultValue(new DefaultCommand().withService(service));
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
