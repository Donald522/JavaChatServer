package service.impl;

import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MonitoringService;
import storage.ClientSessionStorage;

import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 23.02.17
 */

public class MonitoringServiceImpl implements MonitoringService {

    private static final Logger logger = LogManager.getLogger(MonitoringServiceImpl.class);

    private ClientSessionStorage storage;

    public MonitoringServiceImpl(ClientSessionStorage storage) {
        this.storage = storage;
    }

    @Override
    public void start() {
        logger.info("Start storage monitoring");
        Thread thread = new Thread(this::observe);
        thread.setName("Monitoring");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void observe() {
        Map<String, User> onlineUsers = storage.getOnlineUsers();
        while (true) {
            onlineUsers.entrySet().removeIf(e -> {
                Set<Socket> sockets = e.getValue().getSockets();
                sockets.removeIf(Socket::isClosed);
                return log(sockets.isEmpty(), e.getValue());
            });
        }
    }

    private boolean log(boolean empty, User user) {
        if (empty) {
            logger.info("User {} has been removed from active users cache", user);
        }
        return empty;
    }
}
