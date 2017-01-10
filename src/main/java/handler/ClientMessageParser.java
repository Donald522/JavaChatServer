package handler;

import model.command.Command;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public interface ClientMessageParser {

    Command<?> parseInput(String jsonString);

}
