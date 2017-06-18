package service;


import model.User;
import network.model.network.Sendable;

/**
 * Created by Anton Tolkachev.
 * Since 03.06.17
 */

public interface ContactRequestSender {

    void send(Sendable request, User user);

}
