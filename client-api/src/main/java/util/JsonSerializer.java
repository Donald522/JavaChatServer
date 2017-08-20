package util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Response;

import java.io.IOException;

/**
 * Created by Anton Tolkachev.
 * Since 18.06.17
 */

public class JsonSerializer {

    public static String prepareRequest(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object) + System.lineSeparator();
    }

    public static Response prepareResponse(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(str, Response.class);
    }

}
