package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.user.Credentials;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 14.01.17
 */

public class SignInCommand extends AbstractCommand<Credentials> {
    @Override
    public void handle() {
        boolean response;
        response = service.signInUser(argument.getArgument());
        if(!response) {
            throw new RuntimeException("Unknown user or incorrect password");
        }
    }

    @Override
    public Command<Credentials> withArguments(Map<?, ?> args) {
        Credentials credentials = new Credentials((String) args.get(JsonNodes.USERNAME), (String) args.get(JsonNodes.PASSWORD));
        setArgument(new Argument<>(credentials));
        return this;
    }
}
