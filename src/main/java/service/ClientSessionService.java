package service;

import model.Credentials;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public interface ClientSessionService {

    boolean signUpUser(Credentials credentials);

}
