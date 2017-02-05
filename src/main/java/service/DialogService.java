package service;

import model.dialog.Dialog;
import model.dialog.Message;
import model.user.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by Anton Tolkachev.
 * Since 28.01.17
 */

public interface DialogService {

    void start();

    Dialog getDialogById(Integer dialogId);

    boolean createNewDialog(Dialog dialog);

    boolean addUserToDialog(Pair<Integer, User> newUser);

    boolean addUsersToDialog(Pair<Integer, List<User>> newUsers);

    boolean deleteUserFromDialog(Pair<Integer, User> userToDelete);

    boolean deleteDialog(Dialog dialog);

    boolean sendMessage(Message message);

}
