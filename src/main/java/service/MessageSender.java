package service;

import model.dialog.Message;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public interface MessageSender extends Runnable {

    void send(Message message);

}
