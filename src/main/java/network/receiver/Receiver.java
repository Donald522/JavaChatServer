package network.receiver;

import model.command.Command;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

@FunctionalInterface
public interface Receiver {

    Command<?> receive(Socket socket) throws IOException;

}
