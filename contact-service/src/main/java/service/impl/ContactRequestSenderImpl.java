package service.impl;

import model.Status;
import model.User;
import network.model.network.Sendable;
import network.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactRequestSender;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 03.06.17
 */

public class ContactRequestSenderImpl implements ContactRequestSender {

    private static final Logger logger = LogManager.getLogger(ContactRequestSenderImpl.class);

    private final Sender sender;

    public ContactRequestSenderImpl(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void send(Sendable request, User user) {
        if (user.getStatus() != Status.OFFLINE) {
            Set<Socket> sockets = user.getSockets();
            sockets.forEach((socket) -> {
                try {
                    sender.send(socket, request);
                } catch (IOException e) {
                    logger.warn("Can't send a message to client {}", user);
                }
            });
        }
    }
}
