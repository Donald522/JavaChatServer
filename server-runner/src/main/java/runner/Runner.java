package runner;


import server.Server;

import javax.security.auth.RefreshFailedException;
import java.io.IOException;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class Runner {

    public static void main(String[] args) throws IOException, RefreshFailedException, InterruptedException {

        Server server = new Server();
        server.start();

    }

}