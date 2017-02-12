package handler;

import util.JsonNodes;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public interface ClientMessageParser {

    Map<JsonNodes, ?> parse(String jsonString) throws IOException;

    String prepareResponse(Object object) throws IOException;

}
