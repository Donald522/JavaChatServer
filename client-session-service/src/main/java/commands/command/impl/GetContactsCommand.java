package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import model.User;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;

import java.util.Map;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 08.06.17
 */

public class GetContactsCommand extends AbstractCommand<Object> {
    @Override
    public Response handle() {
        Set<User> contactsForUser = service.getContactsForUser(service.getCurrentUser());
        return Response.newBuilder()
                .setStatus(RequestStatus.OK)
                .setMessage(contactsForUser.toString())
                .build();
    }

    @Override
    public Command<Object> withArguments(Map<?, ?> args) {
        setArgument(new Argument<>(new Object()));
        return this;
    }
}
