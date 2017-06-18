package network.sender;



import network.model.network.Sendable;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

@FunctionalInterface
public interface Sender {

    void send(Socket socket, Sendable packet) throws IOException;

}
