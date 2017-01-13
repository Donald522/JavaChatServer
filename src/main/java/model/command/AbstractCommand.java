package model.command;

import service.ClientSessionService;

import java.util.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public abstract class AbstractCommand<T> implements Command<T> {

    protected ClientSessionService service;

    protected Argument<T> argument;

    public AbstractCommand() {}

    public AbstractCommand(Argument<T> argument) {
        this.argument = argument;
    }

    public void setService(ClientSessionService service) {
        this.service = service;
    }

    public Command<T> withService(ClientSessionService service) {
        setService(service);
        return this;
    }

    public void setArgument(Argument<T> argument) {
        this.argument = argument;
    }

    public Argument<T> getArgument() {
        return argument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCommand<?> that = (AbstractCommand<?>) o;
        return Objects.equals(argument, that.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argument);
    }
}
