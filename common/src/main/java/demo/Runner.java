//package demo;
//
//import com.google.common.collect.Sets;
//import dao.core.impl.ClientSessionDao;
//import dao.util.DataSourceProvider;
//import ClientMessageParser;
//import model.dialog.Dialog;
//import model.user.User;
//import network.SocketProvider;
//import network.receiver.Receiver;
//import network.sender.Sender;
//import service.ClientSessionService;
//import service.DialogService;
//import network.service.impl.SocketStreamProvider;
//import storage.ClientSessionStorage;
//
//import javax.security.auth.RefreshFailedException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.HashSet;
//import java.util.List;
//
//import static java.util.Arrays.asList;
//
///**
// * Created by Anton Tolkachev.
// * Since 08.01.17
// */
//
//public class Runner {
//
//    public static void main(String[] args) throws IOException, SQLException, RefreshFailedException {
//
//        final String json = "{\"commands\":\"signup\", " +
//                "\"username\":\"AntonG\", " +
//                "\"password\":\"pass123\"}";
//
//        /*
//        {"commands":"signup", "username":"AntonA", "password":"pass123"}
//
//         */
//
//        System.out.println(Runtime.getRuntime().availableProcessors());
//
//        final String path = "src/main/resources/database.properties";
//
//        DataSourceProvider dataSourceProvider;
//        ClientSessionDao dao;
//        ClientSessionStorage storage;
//        DialogService dialogService;
//        SocketProvider socketProvider;
//        SocketStreamProvider streamProvider;
//        Sender sender;
//        Receiver receiver;
//        ClientMessageParser parser;
//        ClientSessionService service;
//
//        User user1 = new User("anton", "123");
//        User user2 = new User("vlad", "456");
//
//        List<User> userList1 = asList(user1, user2);
//        List<User> userList2 = asList(user2, user1);
//        HashSet<User> users1 = Sets.newHashSet(user1, user2);
//        HashSet<User> users2 = Sets.newHashSet(user2, user1);
//        Dialog dialog1 = new Dialog(users1);
//        Dialog dialog2 = new Dialog(users2);
//
//
//        System.out.println(dialog1.getId()==dialog2.getId());
////        dataSourceProvider = new MysqlDataSourceProvider(path);
////        dao = new ClientSessionDao(dataSourceProvider.getDataSource());
////        storage = new ClientSessionStorage(dao);
////        streamProvider = new SocketStreamProvider();
////        parser = new ClientMessageParserImpl();
////        sender = new SimpleSender(parser, streamProvider);
////        socketProvider = new SocketProvider(sender);
////        dialogService = new DialogServiceImpl(sender);
////        service = new ClientSessionServiceImpl(dao, storage, dialogService, socketProvider);
////        Factory<?> factory = new CommandFactory(new HashMap<String, Command>(){{
////            put("signup", new SignUpCommand().withService(service));
////            put("signin", new SignInCommand().withService(service));
////        }}).withDefaultValue(new DefaultCommand().withService(service));
//
////        receiver = new SimpleReceiver(streamProvider, parser, factory);
////        dialogService.start();
//
////        String s = "{\"USERNAME\":\"anton\", \"PROFILE\":{\"GENDER\":\"male\"}}";
////        Map<JsonNodes, ?> parse = parser.parse(s);
////        Map<String, String> map = (Map<String, String>) parse.get(JsonNodes.PROFILE);
////        System.out.println(((Map<String, String>) parse.get(JsonNodes.PROFILE)).get("GENDER"));
//
////        User user = new User(123, "user", "pass");
////        user.setProfile(Profile.newBuilder()
////                .setGender("male")
////                .setDateOfBirth(new Date(56251235))
////                .setCountry("Russia")
////                .build()
////        );
////
////        String response = parser.prepareResponse(user);
////        System.out.println(response);
//
////        Date dob = Utils.getDate(map.getOrDefault("DOB", StringUtils.EMPTY));
////        System.out.println(dob);
//
//    }
//
//}
