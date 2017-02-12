package service;

import model.dialog.Dialog;
import model.network.Sendable;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public interface MessageSender extends Runnable {

    void send(Sendable message, Dialog dialog);

}
