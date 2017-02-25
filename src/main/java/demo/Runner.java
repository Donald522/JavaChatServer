package demo;

import dao.core.ClientSessionDao;
import dao.util.DataSourceProvider;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import network.SocketProvider;
import network.receiver.Receiver;
import network.sender.Sender;
import org.apache.commons.lang3.StringUtils;
import service.ClientSessionService;
import service.DialogService;
import service.impl.SocketStreamProvider;
import storage.ClientSessionStorage;
import util.JsonNodes;
import util.Utils;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException, SQLException, RefreshFailedException {

        final String json = "{\"command\":\"signup\", " +
                "\"username\":\"AntonG\", " +
                "\"password\":\"pass123\"}";

        /*
        {"command":"signup", "username":"AntonA", "password":"pass123"}

         */

        final String path = "src/main/resources/database.properties";

        DataSourceProvider dataSourceProvider;
        ClientSessionDao dao;
        ClientSessionStorage storage;
        DialogService dialogService;
        SocketProvider socketProvider;
        SocketStreamProvider streamProvider;
        Sender sender;
        Receiver receiver;
        ClientMessageParser parser;
        ClientSessionService service;

//        dataSourceProvider = new MysqlDataSourceProvider(path);
//        dao = new ClientSessionDao(dataSourceProvider.getDataSource());
//        storage = new ClientSessionStorage(dao);
//        streamProvider = new SocketStreamProvider();
        parser = new ClientMessageParserImpl();
//        sender = new SimpleSender(parser, streamProvider);
//        socketProvider = new SocketProvider(sender);
//        dialogService = new DialogServiceImpl(sender);
//        service = new ClientSessionServiceImpl(dao, storage, dialogService, socketProvider);
//        Factory<?> factory = new CommandFactory(new HashMap<String, Command>(){{
//            put("signup", new SignUpCommand().withService(service));
//            put("signin", new SignInCommand().withService(service));
//        }}).withDefaultValue(new DefaultCommand().withService(service));

//        receiver = new SimpleReceiver(streamProvider, parser, factory);
//        dialogService.start();

        String s = "{\"USERNAME\":\"anton\", \"PROFILE\":{\"GENDER\":\"male\"}}";
        Map<JsonNodes, ?> parse = parser.parse(s);
        Map<String, String> map = (Map<String, String>) parse.get(JsonNodes.PROFILE);
//        System.out.println(((Map<String, String>) parse.get(JsonNodes.PROFILE)).get("GENDER"));

//        User user = new User(123, "user", "pass");
//        user.setProfile(Profile.newBuilder()
//                .setGender("male")
//                .setDateOfBirth(new Date(56251235))
//                .setCountry("Russia")
//                .build()
//        );
//
//        String response = parser.prepareResponse(user);
//        System.out.println(response);

        Date dob = Utils.getDate(map.getOrDefault("DOB", StringUtils.EMPTY));
        System.out.println(dob);

    }

}
