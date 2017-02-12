package network.receiver.impl;

import handler.ClientMessageParser;
import model.command.Command;
import network.receiver.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.StreamProvider;
import util.Factory;
import util.JsonNodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

public class SimpleReceiver implements Receiver {

    private static final Logger logger = LogManager.getLogger(SimpleReceiver.class);

    private StreamProvider streamProvider;
    private ClientMessageParser parser;
    private Factory<?> factory;

    public SimpleReceiver(StreamProvider streamProvider, ClientMessageParser parser, Factory<?> factory) {
        this.streamProvider = streamProvider;
        this.parser = parser;
        this.factory = factory;
    }

    @Override
    public Command<?> receive(Socket socket) throws IOException {
        try {
            BufferedReader reader = (BufferedReader) streamProvider.getReader(socket);
            String line = reader.readLine();
            if(line == null) {
                return null;
            }
            Map<JsonNodes, ?> nodesMap = parser.parse(line);
            Command<?> command = (Command<?>) factory.getObject((String) nodesMap.get(JsonNodes.COMMAND));
            return command.withArguments(nodesMap);
        } catch (IOException e) {
            logger.info("Error happened when read the socket. Cause:  " + e.getMessage());
            throw e;
        }
    }
}
