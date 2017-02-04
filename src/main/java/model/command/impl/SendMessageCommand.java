package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.dialog.Message;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class SendMessageCommand extends AbstractCommand<Message> {
    @Override
    public void handle() {
        service.sendMessage(argument.getArgument());
    }

    @Override
    public Command<Message> withArguments(Map<?, ?> args) {
        Message message = Message.newBuilder()
                .setDialogId(Integer.parseInt((String) args.get(JsonNodes.DIALOG_ID)))
                .setMessage((String) args.get(JsonNodes.MESSAGE))
                .build();
        setArgument(new Argument<>(message));
        return this;
    }
}
