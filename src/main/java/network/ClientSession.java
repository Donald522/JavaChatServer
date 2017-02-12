package network;

import model.command.Command;
import model.network.impl.Response;
import network.receiver.Receiver;
import network.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestStatus;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Anton Tolkachev.
 * Since 15.01.17
 */

public class ClientSession {

    private static final Logger logger = LogManager.getLogger(ClientSession.class);

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    public ClientSession(Socket socket, Sender sender, Receiver receiver) {
        this.socket = socket;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void run() {
        try {
            mainLoop();
        } catch (IOException e) {
            logger.warn("Error happened when closing client's socket. Returning.");
        }
    }

    private void mainLoop() throws IOException {
        try {
            while (!Thread.interrupted()) {
                if (!processClient()) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.info("Error during client processing. Cause: " + e.getMessage());
        } finally {
            socket.close();
        }
        logger.info("Thread {} was interrupted because socket is disconnected", Thread.currentThread().getId());
    }

    private boolean processClient() throws IOException {
        try {
            Command<?> command = receiver.receive(socket);
            if(command == null) {
                return false;
            }
            Response response = command.handle();
            sender.send(socket, response);
        } catch(SocketTimeoutException e) {
            logger.info("No input from socket {} for 1 minute", socket);
            return true;
        } catch (RuntimeException | IOException e) {
            logger.info("Error occurs while handling client's input. Cause: " + e.getMessage());
            Response response = Response.newBuilder()
                    .setStatus(RequestStatus.FAIL)
                    .setMessage(e.getMessage())
                    .build();
            sender.send(socket, response);
        }
        return true;
    }
}
