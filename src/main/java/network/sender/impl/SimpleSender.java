package network.sender.impl;

import handler.ClientMessageParser;
import model.network.Sendable;
import network.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.StreamProvider;

import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

public class SimpleSender implements Sender {

    private static final Logger logger = LogManager.getLogger(SimpleSender.class);

    private StreamProvider streamProvider;
    private ClientMessageParser parser;

    public SimpleSender(ClientMessageParser parser, StreamProvider streamProvider) {
        this.parser = parser;
        this.streamProvider = streamProvider;
    }

    @Override
    public void send(Socket socket, Sendable packet) throws IOException {
        try {
            Writer writer = streamProvider.getWriter(socket);
            String response = parser.prepareResponse(packet);
            writer.write(response);
            if(streamProvider.isBuffered()) {
                writer.flush();
            }
        } catch (IOException e) {
            logger.info("Error happened when write to socket. Cause:  " + e.getMessage());
            throw e;
        }
    }
}
