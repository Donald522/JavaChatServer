package handler.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import handler.ClientMessageParser;
import model.command.Command;
import util.Factory;
import util.JsonNodes;

import java.io.IOException;
import java.util.Map;

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
    public Command<?> parseInput(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<JsonNodes, ?> nodes = mapper.readValue(jsonString, new TypeReference<Map<JsonNodes,?>>(){});
        Command<?> command = (Command<?>) factory.getObject((String) nodes.get(JsonNodes.COMMAND));
        return command.withArguments(nodes);
    }

    @Override
    public String prepareResponse(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object) + System.lineSeparator();
    }
}
