package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.network.impl.Response;
import model.user.Credentials;
import util.JsonNodes;
import util.RequestStatus;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class SignUpCommand extends AbstractCommand<Credentials> {

    @Override
    public Response handle() {
        RequestStatus status = RequestStatus.OK;
        String message = "";
        boolean response;
        response = service.signUpUser(argument.getArgument());
        if(!response) {
            status = RequestStatus.FAIL;
            message = "Name is already used";
        }
        return Response.newBuilder()
                .setStatus(status)
                .setMessage(message)
                .build();
    }

    @Override
    public Command<Credentials> withArguments(final Map<?, ?> args) {
        Credentials credentials = new Credentials(
                (String) args.get(JsonNodes.USERNAME),
                (String) args.get(JsonNodes.PASSWORD));
        setArgument(new Argument<>(credentials));
        return this;
    }

}
