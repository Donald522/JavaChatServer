package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import model.RelationRequest;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;

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
