package commands.command;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Argument<?> argument1 = (Argument<?>) o;
        return Objects.equals(argument, argument1.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argument);
    }
}
