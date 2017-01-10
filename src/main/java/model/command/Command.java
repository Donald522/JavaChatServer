package model.command;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public interface Command<T> {

    void handle();


    Class getArgType();
}
