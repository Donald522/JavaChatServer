package handler.impl;

import handler.ClientMessageParser;
import model.command.Command;
import util.Factory;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class ClientMessageParserImpl implements ClientMessageParser {

    private Factory<?> factory;

    public ClientMessageParserImpl(Factory<?> factory) {
        this.factory = factory;
    }

    @Override
    public Command<?> parseInput(String jsonString) {
        return (Command<?>) factory.getObject(jsonString);
    }
}
