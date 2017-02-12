package handler.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import handler.ClientMessageParser;
import util.JsonNodes;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class ClientMessageParserImpl implements ClientMessageParser {

    public ClientMessageParserImpl() {
    }

    @Override
    public Map<JsonNodes, ?> parse(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<Map<JsonNodes,?>>(){});
    }

    @Override
    public String prepareResponse(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object) + System.lineSeparator();
    }
}
