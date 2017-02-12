package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.network.impl.Response;
import util.RequestStatus;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 14.01.17
 */

public class DefaultCommand extends AbstractCommand<Object> {

    @Override
    public Response handle() {
        service.handleDefaultCommand();
        return Response.newBuilder()
                .setStatus(RequestStatus.OK)
                .build();
    }

    @Override
    public Command<Object> withArguments(Map<?, ?> args) {
        setArgument(new Argument<>(new Object()));
        return this;
    }
}
