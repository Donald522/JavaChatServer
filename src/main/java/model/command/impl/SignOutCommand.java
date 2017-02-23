package model.command.impl;

import model.command.AbstractCommand;
import model.command.Command;
import model.network.impl.Response;
import util.RequestStatus;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 23.02.17
 */

public class SignOutCommand extends AbstractCommand<Object> {
    @Override
    public Response handle() {
        boolean signOut = service.signOut();
        Response response = Response.newBuilder()
                .setStatus(signOut ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
        if(!signOut) {
            response.setMessage("User is already offline");
        }
        return response;
    }

    @Override
    public Command<Object> withArguments(Map<?, ?> args) {
        return this;
    }
}
