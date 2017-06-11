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
 * Since 11.06.17
 */

public class RejectRequestCommand extends AbstractCommand<RelationRequest> {
    @Override
    public Response handle() {
        boolean rejectRequest = service.rejectRequest(argument.getArgument());
        return Response.newBuilder()
                .setStatus(rejectRequest ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    public Command<RelationRequest> withArguments(Map<?, ?> args) {
        String first = (String) args.get(JsonNodes.USER_1);
        String second = (String) args.get(JsonNodes.USER_2);
        RelationRequest relationRequest = new RelationRequest.Builder()
                .setFirst(first)
                .setSecond(second)
                .build();
        setArgument(new Argument<>(relationRequest));
        return this;
    }
}
