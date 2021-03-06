package commands.command.impl;



import commands.command.Argument;
import commands.command.Command;
import model.Dialog;
import model.User;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;
import util.JsonNodes;

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
        boolean newDialog = service.createNewDialog(argument.getArgument());
        return Response.newBuilder()
                .setStatus(newDialog ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Command<Dialog> withArguments(Map<?, ?> args) {
        try {
            List<String> usernames = new ArrayList<>((Collection<String>) args.get(JsonNodes.USERS));
            List<User> users = new ArrayList<>(usernames.size() + 1);
            for (String username : usernames) {
                User user = service.getUserByName(username);
                users.add(user);
            }
            User currentUser = service.getCurrentUser();
            users.add(currentUser);
            Dialog dialog = new Dialog(users);
            dialog.setLeader(currentUser);
            setArgument(new Argument<>(dialog));
        } catch (ClassCastException e) {
            throw new RuntimeException("Not a list of users passed", e);
        }
        return this;
    }
}
