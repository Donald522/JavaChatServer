package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.contact.RelationRequest;
import model.network.impl.Response;
import util.RequestStatus;

import java.util.Map;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 11.06.17
 */

public class GetInboundRequestsCommand extends AbstractCommand<Object> {
    @Override
    public Response handle() {
        Set<RelationRequest> inboundRequests = service.getInboundRequests(service.getCurrentUser());
        return Response.newBuilder()
                .setStatus(RequestStatus.OK)
                .setMessage(inboundRequests.toString())
                .build();
    }

    @Override
    public Command<Object> withArguments(Map<?, ?> args) {
        setArgument(new Argument<>(new Object()));
        return this;
    }
}
