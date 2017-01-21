package network;

import handler.ClientMessageParser;
import model.command.Command;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class ClientSession {

    private final Socket socket;
    private final ClientMessageParser parser;

    public ClientSession(Socket socket, ClientMessageParser parser) {
        this.socket = socket;
        this.parser = parser;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    this.socket.getInputStream()), "UTF-8"))) {
            while (true) {
                String line = reader.readLine();
                if(line == null) {
                    break;
                }
                Command<?> command = parser.parseInput(line);
                command.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
