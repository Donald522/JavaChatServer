package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.network.impl.Response;
import model.user.User;
import util.RequestStatus;

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
