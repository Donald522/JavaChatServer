package network.receiver;


import java.io.IOException;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

@FunctionalInterface
public interface Receiver<T> {

    T receive(Socket socket) throws IOException;

}
