package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class Server {

    private ServerSocket serverSocket;
    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        Socket client = serverSocket.accept();
        InputStream inputStream = client.getInputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(inputStream),
                        "UTF-8"
                )
        );
        while(client.isConnected()) {
            System.out.println(in.readLine());
        }
    }


}
