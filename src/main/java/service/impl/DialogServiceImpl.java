package service.impl;

import model.dialog.Dialog;
import model.user.User;
import org.apache.commons.lang3.tuple.Pair;
import service.DialogService;
import service.MessageSender;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class DialogServiceImpl implements DialogService {

    private Map<Integer, Dialog> dialogs;

    private MessageSender messageSender;

    public DialogServiceImpl() {
        this.dialogs = new ConcurrentHashMap<>();
        this.messageSender = new MessageSenderImpl(dialogs.values());
    }

    @Override
    public void start() {
        new Thread(messageSender).start();
    }

    @Override
    public boolean createNewDialog(Dialog dialog) {
        return dialogs.putIfAbsent(dialog.getId(), dialog) == null;
    }

    @Override
    public boolean addUserToDialog(Pair<Integer, User> newUser) {
        Integer dialogId = newUser.getKey();
        return dialogs.get(dialogId).addUser(newUser.getValue());
    }

    @Override
    public boolean addUsersToDialog(Pair<Integer, List<User>> newUsers) {
        Integer dialogId = newUsers.getKey();
        return dialogs.get(dialogId).addUsers(newUsers.getValue());
    }

    @Override
    public boolean deleteUserFromDialog(Pair<Integer, User> userToDelete) {
        Integer dialogId = userToDelete.getKey();
        return dialogs.get(dialogId).deleteUser(userToDelete.getValue());
    }

}
