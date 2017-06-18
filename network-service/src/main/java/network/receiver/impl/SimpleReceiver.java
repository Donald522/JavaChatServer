package network.receiver.impl;

import handler.ClientMessageParser;
import network.receiver.Receiver;
import network.service.StreamProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class SimpleReceiver<T> implements Receiver<T> {

    private static final Logger logger = LogManager.getLogger(SimpleReceiver.class);

    private StreamProvider streamProvider;
    private ClientMessageParser parser;
    private Factory<T> factory;

    public SimpleReceiver(StreamProvider streamProvider, ClientMessageParser parser, Factory<T> factory) {
        this.streamProvider = streamProvider;
        this.parser = parser;
        this.factory = factory;
    }

    @Override
    public T receive(Socket socket) throws IOException {
        try {
            BufferedReader reader = (BufferedReader) streamProvider.getReader(socket);
            String line = reader.readLine();
            if(line == null) {
                return null;
            }
            Map<JsonNodes, ?> nodesMap = parser.parse(line);
            return factory.getObject(nodesMap);
        } catch (IOException e) {
            logger.info("Error happened when read the socket. Cause:  " + e.getMessage());
            throw e;
        }
    }
}
