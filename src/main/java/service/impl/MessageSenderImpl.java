package service.impl;

import model.dialog.Dialog;
import model.network.Sendable;
import model.user.Status;
import model.user.User;
import network.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MessageSender;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class MessageSenderImpl implements MessageSender {

    private static final Logger logger = LogManager.getLogger(MessageSenderImpl.class);

    private Sender sender;
    private Collection<Dialog> dialogs;

    public MessageSenderImpl(Sender sender, Collection<Dialog> dialogs) {
        this.dialogs = dialogs;
        this.sender = sender;
    }

    @Override
    public void send(Sendable message, Dialog dialog) {
        List<User> users = dialog.getUsers();
        users.stream()
                .filter((user) -> user.getStatus() != Status.OFFLINE)
                .forEach((user) -> {
            Set<Socket> sockets = user.getSockets();
            sockets.forEach((socket) -> {
                try {
                    sender.send(socket, message);
                } catch (IOException e) {
                    logger.warn("Can't send a message to client {}", user);
                }
            });
        });
    }

    @Override
    public void run() {
        while (true) {
            dialogs.forEach((dialog) -> {
                Sendable message = dialog.getMessages().poll();
                if (message != null) {
                    send(message, dialog);
                    //TODO: also save to database
                }
            });
        }
    }

    public void setDialogs(Set<Dialog> dialogs) {
        this.dialogs = dialogs;
    }
}
