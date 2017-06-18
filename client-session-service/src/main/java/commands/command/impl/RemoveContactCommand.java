package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import model.Relation;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.06.17
 */

public class RemoveContactCommand extends AbstractCommand<Relation> {
    @Override
    public Response handle() {
        boolean removeContact = service.removeContact(argument.getArgument());
        return Response.newBuilder()
                .setStatus(removeContact ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    public Command<Relation> withArguments(Map<?, ?> args) {
        String first = (String) args.get(JsonNodes.USER_1);
        String second = (String) args.get(JsonNodes.USER_2);
        Relation relation = new Relation.Builder()
                .setFirst(first)
                .setSecond(second)
                .build();
        setArgument(new Argument<>(relation));
        return this;
    }
}
