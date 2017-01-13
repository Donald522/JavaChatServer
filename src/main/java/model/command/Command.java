package model.command;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public interface Command<T> {

    void handle();

    Command<T> withArguments(Map<?, ?> args);

}
