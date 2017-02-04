package model.command;

import network.Response;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public interface Command<T> {

    Response handle();

    Command<T>  withArguments(Map<?, ?> args);

}
