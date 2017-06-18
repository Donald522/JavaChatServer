package service.impl;

import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpiringMap;
import network.model.network.RequestStatus;
import network.model.network.impl.Popup;
import network.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anton Tolkachev.
 * Since 18.01.17
 */

public class SocketProvider {

    private static final Logger logger = LogManager.getLogger(SocketProvider.class);

    private final Sender sender;

    public SocketProvider(Sender sender) {
        this.sender = sender;
    }

    private Map<Long, Socket> socketsCache = ExpiringMap.builder()
            .expiration(5, TimeUnit.MINUTES)
            .expirationListener(new ExpirationListener<Long, Socket>() {
                @Override
                public void expired(Long aLong, Socket socket) {
                    try {
                        logger.info("Remove inactive socket from temporary cache.");
                        sender.send(socket, Popup.newBuilder()
                                .setStatus(RequestStatus.FAIL)
                                .setMessage("Session closed")
                                .build()
                        );
                        socket.close();
                    } catch (IOException e) {
                        logger.warn("Exception during closing socket");
                    }
                }
            })
            .build();

    public Socket getSocket() {
        return socketsCache.remove(Thread.currentThread().getId());
    }

    public void setSocket(Socket socket) {
        socketsCache.put(Thread.currentThread().getId(), socket);
    }

    public boolean contains() {
        return socketsCache.containsKey(Thread.currentThread().getId());
    }
}
