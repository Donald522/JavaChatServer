package network.model;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 23.07.17
 */

public class Session {

    private static final int     DEFAULT_PORT = 7788;
    private static final String  DEFAULT_HOST = "localhost";

    private final int port;
    private final String host;
    private final Socket socket;

    public Session() throws IOException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public Session(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
        this.socket = new Socket(host, port);
    }

    public Socket getSocket() {
        return socket;
    }
}
