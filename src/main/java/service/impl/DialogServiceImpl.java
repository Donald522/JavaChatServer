package service.impl;

import model.dialog.Dialog;
import model.dialog.Message;
import model.network.Sendable;
import model.network.impl.DecoratedMessage;
import model.user.User;
import network.sender.Sender;
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

    public DialogServiceImpl(Sender sender) {
        this.dialogs = new ConcurrentHashMap<>();
        this.messageSender = new MessageSenderImpl(sender, dialogs.values());
    }

    @Override
    public void start() {
        new Thread(messageSender).start();
    }

    @Override
    public Dialog getDialogById(Integer dialogId) {
        return dialogs.get(dialogId);
    }

    @Override
    public boolean createNewDialog(Dialog dialog) {
        return dialogs.putIfAbsent(dialog.getId(), dialog) == null;
    }

    @Override
    public boolean addUserToDialog(Pair<Integer, User> newUser) {
        Integer dialogId = newUser.getKey();
        if(!dialogs.keySet().contains(dialogId)) {
            return false;
        }
        return dialogs.get(dialogId).addUser(newUser.getValue());
    }

    @Override
    public boolean addUsersToDialog(Pair<Integer, List<User>> newUsers) {
        Integer dialogId = newUsers.getKey();
        if(!dialogs.keySet().contains(dialogId)) {
            return false;
        }
        return dialogs.get(dialogId).addUsers(newUsers.getValue());
    }

    @Override
    public boolean deleteUserFromDialog(Pair<Integer, User> userToDelete) {
        Integer dialogId = userToDelete.getKey();
        if(!dialogs.keySet().contains(dialogId)) {
            return false;
        }
        Dialog dialog = dialogs.get(dialogId);
        boolean deleteUser = dialog.deleteUser(userToDelete.getValue());
        if(dialog.getUsers().isEmpty()) {
            boolean deleteDialog = deleteDialog(dialog);
            deleteUser &= deleteDialog;
        }
        return deleteUser;
    }

    @Override
    public boolean deleteDialog(Dialog dialog) {
        return dialogs.remove(dialog.getId()) == dialog;
    }

    @Override
    public boolean sendMessage(Message message) {
        int dialogId = message.getDialogId();
        if(!dialogs.keySet().contains(dialogId)) {
            return false;
        }
        Sendable decoratedMessage = new DecoratedMessage(message);
        return dialogs.get(dialogId).addMessage(decoratedMessage);
    }

}
