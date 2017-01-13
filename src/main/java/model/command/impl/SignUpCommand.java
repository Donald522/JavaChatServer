package model.command.impl;

import model.Credentials;
import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class SignUpCommand extends AbstractCommand<Credentials> {

    @Override
    public void handle() {
        service.signUpUser(argument.getArgument());
    }

    @Override
    public Command<Credentials> withArguments(Map<?, ?> args) {
        Credentials credentials = new Credentials((String)args.get("username"), (String) args.get("password"));
        setArgument(new Argument<>(credentials));
        return this;
    }

}
