package commands.command.impl;


import commands.command.Argument;
import commands.command.Command;
import model.Credentials;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 14.01.17
 */

public class SignInCommand extends AbstractCommand<Credentials> {
    @Override
    public Response handle() {
        RequestStatus status = RequestStatus.OK;
        String message = "";
        boolean response;
        response = service.signInUser(argument.getArgument());
        if(!response) {
            status = RequestStatus.FAIL;
            message = "Unknown user or incorrect password";
        }
        return Response.newBuilder()
                .setStatus(status)
                .setMessage(message)
                .build();
    }

    @Override
    public Command<Credentials> withArguments(Map<?, ?> args) {
        Credentials credentials = new Credentials((String) args.get(JsonNodes.USERNAME), (String) args.get(JsonNodes.PASSWORD));
        setArgument(new Argument<>(credentials));
        return this;
    }
}
