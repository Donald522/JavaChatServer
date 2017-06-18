package commands.command.impl;

import commands.command.Argument;
import commands.command.Command;
import model.Message;
import network.model.network.RequestStatus;
import network.model.network.impl.Response;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class SendMessageCommand extends AbstractCommand<Message> {
    @Override
    public Response handle() {
        boolean sendMessage = service.sendMessage(argument.getArgument());
        return Response.newBuilder()
                .setStatus(sendMessage ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    public Command<Message> withArguments(Map<?, ?> args) {
        Message message = Message.newBuilder()
                .setDialogId(Integer.parseInt((String) args.get(JsonNodes.DIALOG_ID)))
                .setMessage((String) args.get(JsonNodes.MESSAGE))
                .setFromUser(service.getCurrentUser())
                .build();
        setArgument(new Argument<>(message));
        return this;
    }
}
