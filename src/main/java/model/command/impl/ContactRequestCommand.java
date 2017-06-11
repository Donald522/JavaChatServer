package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.contact.RelationRequest;
import model.network.impl.Response;
import util.JsonNodes;
import util.RequestStatus;

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
