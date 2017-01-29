package service.impl;

import model.dialog.Dialog;
import model.dialog.Message;
import model.user.User;
import service.MessageSender;
import service.StreamProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class MessageSenderImpl implements MessageSender {

    private StreamProvider streamProvider;
    private Map<Integer, Dialog> dialogs;

    public MessageSenderImpl(Map<Integer, Dialog> dialogs, StreamProvider streamProvider) {
        this.dialogs = dialogs;
        this.streamProvider = streamProvider;
    }

    @Override
    public void send(Message message) {
        Dialog dialog = dialogs.get(message.getDialogId());
        List<User> users = dialog.getUsers();
//        users.forEach((user) -> {
//            streamProvider.getWriter()
//        });
    }

    @Override
    public void run() {

    }
}
