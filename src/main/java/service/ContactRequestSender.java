package service;

import model.network.Sendable;
import model.user.User;

/**
 * Created by Anton Tolkachev.
 * Since 03.06.17
 */

public interface ContactRequestSender {

    void send(Sendable request, User user);

}
