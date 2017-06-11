package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.contact.Relation;
import model.network.impl.Response;
import util.JsonNodes;
import util.RequestStatus;

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
