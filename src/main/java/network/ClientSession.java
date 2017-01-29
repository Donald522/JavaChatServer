package network;

import handler.ClientMessageParser;
import model.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger(ClientSession.class);

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
                if (processClient(reader)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean processClient(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if(line == null) {
            return true;
        }
        try {
            Command<?> command = parser.parseInput(line);
            command.handle();
        } catch (RuntimeException | IOException e) {
            logger.info("Error occurs while handling client's input. Cause: " + e.getMessage());
            // send a response to the client. Format of response will be agreed later.
        }
        return false;
    }
}
