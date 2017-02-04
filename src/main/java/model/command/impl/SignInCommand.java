package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.user.Credentials;
import network.Response;
import util.JsonNodes;
import util.RequestStatus;

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
