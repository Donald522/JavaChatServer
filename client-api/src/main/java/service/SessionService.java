package service;

import model.Credentials;
import model.Message;
import model.Profile;
import model.Relation;
import model.RelationRequest;
import model.User;
import service.impl.SimpleSessionService.Listener;

import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 18.06.17
 */

public interface SessionService {

    boolean signUpUser(Credentials credentials);

    boolean signInUser(Credentials credentials);

    boolean updateUserProfile(Profile profile);

    boolean signOut();

    boolean createNewDialog(Iterable<User> users);

    boolean sendMessage(Message message) throws InterruptedException;

    boolean contactRequest(RelationRequest request);

    Set<User> getContactsForUser(User user);

    Set<RelationRequest> getInboundRequests(User user);

    Set<RelationRequest> getOutboundRequests(User user);

    boolean approveRequest(RelationRequest request);

    boolean rejectRequest(RelationRequest request);

    boolean removeContact(Relation relation);

    Listener getListener();

}
