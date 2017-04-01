package service;

import model.contact.Request;
import model.user.User;

import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public interface ContactService {

    boolean request(Request request);

    boolean approve(Request request);

    boolean reject(Request request);

    Set<User> getContacts(User user);

    Set<User> findContactsByName(String name);

}
