package service;

import model.dialog.Dialog;
import model.dialog.Message;
import model.user.Credentials;
import model.user.Profile;
import model.user.User;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public interface ClientSessionService {

    User getUserByName(String username);

    User getCurrentUser();

    boolean signUpUser(Credentials credentials);

    boolean signInUser(Credentials credentials);

    boolean updateUserProfile(Profile profile);

    boolean signOut();

    void handleDefaultCommand();

    boolean createNewDialog(Dialog dialog);

    boolean sendMessage(Message message);

}
