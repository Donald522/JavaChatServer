package service;

import model.contact.Relation;
import model.user.User;

import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 01.04.17
 */

public interface ContactService {

    boolean request(Relation request);

    boolean approve(Relation request);

    boolean reject(Relation request);

    Set<User> getContacts(User user);

    Set<User> findContactsByName(String name);

}
