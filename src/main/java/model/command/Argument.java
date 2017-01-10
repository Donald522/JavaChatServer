package model.command;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class Argument<T> {
    private T argument;

    public Argument(T argument) {
        this.argument = argument;
    }

    public T getArgument() {
        return argument;
    }
}
