package model.command.impl;

import model.Credentials;
import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import util.JsonNodes;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class SignUpCommand extends AbstractCommand<Credentials> {

    @Override
    public void handle() {
        boolean response;
        response = service.signUpUser(argument.getArgument());
        if(!response) {
            throw new RuntimeException("Name is already used");
        }
    }

    @Override
    public Command<Credentials> withArguments(Map<?, ?> args) {
        Credentials credentials = new Credentials((String) args.get(JsonNodes.USERNAME), (String) args.get(JsonNodes.PASSWORD));
        setArgument(new Argument<>(credentials));
        return this;
    }

}
