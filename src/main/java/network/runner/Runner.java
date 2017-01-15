package network.runner;

import network.Server;

import java.io.IOException;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException {
        Server server = new Server(1234);
        server.run();
    }

}
