package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import model.RelationRequest;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 30.04.17
 */

public class ContactRequestCommand extends AbstractCommand<RelationRequest> {
    @Override
    public Response handle() {
        boolean contactRequest = service.contactRequest(argument.getArgument());
        return Response.newBuilder()
                .setStatus(contactRequest ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    public Command<RelationRequest> withArguments(Map<?, ?> args) {
        String first = (String) args.get(JsonNodes.USER_1);
        String second = (String) args.get(JsonNodes.USER_2);
        RelationRequest relationRequest = new RelationRequest.Builder()
                .setFirst(first)
                .setSecond(second)
                .setMessage((String) args.get(JsonNodes.MESSAGE))
                .setStatus(0).build();
        setArgument(new Argument<>(relationRequest));
        return this;
    }
}
