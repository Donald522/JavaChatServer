package service;

import model.contact.Relation;
import model.contact.RelationRequest;
import model.dialog.Dialog;
import model.dialog.Message;
import model.user.Credentials;
import model.user.Profile;
import model.user.User;

import java.util.Set;

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

    boolean contactRequest(RelationRequest request);

    Set<User> getContactsForUser(User user);

    Set<RelationRequest> getInboundRequests(User user);

    Set<RelationRequest> getOutboundRequests(User user);

    boolean approveRequest(RelationRequest request);

    boolean rejectRequest(RelationRequest request);

    boolean removeContact(Relation relation);

}
