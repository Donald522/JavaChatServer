package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.dialog.Message;
import network.Response;
import util.JsonNodes;
import util.RequestStatus;

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
