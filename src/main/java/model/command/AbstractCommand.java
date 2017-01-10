package model.command;

import service.ClientSessionService;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public abstract class AbstractCommand<T> implements Command<T> {

    protected ClientSessionService service;

    protected Argument<T> argument;

    @Override
    public Class getArgType() {
        return argument.getArgument().getClass();
    }

}
