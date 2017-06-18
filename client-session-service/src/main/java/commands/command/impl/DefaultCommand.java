package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;

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
