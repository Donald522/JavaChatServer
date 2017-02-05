package network;

import handler.ClientMessageParser;
import model.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.StreamProvider;
import service.impl.SocketStreamProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
        StreamProvider socketStreamProvider = new SocketStreamProvider();
        try (BufferedReader reader = (BufferedReader) socketStreamProvider.getReader(socket);
             Writer writer = socketStreamProvider.getWriter(socket)
        ) {
            while (!Thread.interrupted()) {
                if (!processClient(reader, writer)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Thread {} was interrupted because socket is disconnected", Thread.currentThread().getId());
    }

    private boolean processClient(BufferedReader reader, Writer writer) throws IOException {
        String preparedResponse;
        try {
            String line = reader.readLine();
            if(line == null) {
                return false;
            }
            Command<?> command = parser.parseInput(line);
            Response response = command.handle();
            preparedResponse = parser.prepareResponse(response);
        } catch(SocketTimeoutException e) {
            logger.info("No input from socket {} for 1 minute", socket);
            return true;
        } catch (RuntimeException | IOException e) {
            logger.info("Error occurs while handling client's input. Cause: " + e.getMessage());
            preparedResponse = "{\"status\":\"FAIL\"}" + System.lineSeparator();
        }
        writer.write(preparedResponse);
        writer.flush();
        return true;
    }
}
