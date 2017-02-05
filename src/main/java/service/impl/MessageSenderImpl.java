package service.impl;

import model.dialog.Dialog;
import model.dialog.Message;
import model.user.Status;
import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MessageSender;
import service.StreamProvider;

import java.io.IOException;
import java.io.Writer;
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

    private StreamProvider streamProvider;
    private Collection<Dialog> dialogs;

    public MessageSenderImpl(Collection<Dialog> dialogs) {
        this.dialogs = dialogs;
        this.streamProvider = new SocketStreamProvider();
    }

    public MessageSenderImpl(Collection<Dialog> dialogs, StreamProvider streamProvider) {
        this.dialogs = dialogs;
        this.streamProvider = streamProvider;
    }

    @Override
    public void send(Message message, Dialog dialog) {
        List<User> users = dialog.getUsers();
        users.stream()
                .filter((user) -> user.getStatus() != Status.OFFLINE)
                .forEach((user) -> {
            Set<Socket> sockets = user.getSockets();
            sockets.forEach((socket) -> {
                try {
                    Writer writer = streamProvider.getWriter(socket);
                    writer.write(message.getMessage());
                    writer.flush();
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
                Message message = dialog.getMessages().poll();
                if (message != null) {
                    send(message, dialog);
                    //TODO: also save to database
                }
            });
        }
    }

    public void setStreamProvider(StreamProvider streamProvider) {
        this.streamProvider = streamProvider;
    }

    public void setDialogs(Set<Dialog> dialogs) {
        this.dialogs = dialogs;
    }
}
