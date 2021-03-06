package service;


import model.Relation;
import model.RelationRequest;
import model.User;

import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 01.04.17
 */

public interface ContactService {

    boolean request(RelationRequest request);

    boolean approve(RelationRequest request);

    boolean reject(RelationRequest request);

    boolean removeContact(Relation relation);

    Set<User> getContacts(User user);

    Set<RelationRequest> getInboundRequests(User user);

    Set<RelationRequest> getOutboundRequests(User user);

    Set<User> findContactsByName(String name);

}
