package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.dialog.Dialog;
import model.user.User;
import network.Response;
import util.JsonNodes;
import util.RequestStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 28.01.17
 */

public class CreateDialogCommand extends AbstractCommand<Dialog> {

    @Override
    public Response handle() {
        service.createNewDialog(argument.getArgument());
        return Response.newBuilder()
                .setStatus(RequestStatus.OK)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Command<Dialog> withArguments(Map<?, ?> args) {
        List<User> users = new ArrayList<>();
        try {
            List<String> usernames = new ArrayList<>((Collection<String>) args.get(JsonNodes.USERS));
            for (String username : usernames) {
                User user = service.getUserByName(username);
                users.add(user);
            }
            Dialog dialog = new Dialog(users);
            setArgument(new Argument<>(dialog));
        } catch (ClassCastException e) {
            throw new RuntimeException("Not a list of users passed", e);
        }
        return this;
    }
}
