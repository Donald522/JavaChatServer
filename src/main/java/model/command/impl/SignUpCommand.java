package model.command.impl;

import model.Credentials;
import model.command.AbstractCommand;
import model.command.Argument;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class SignUpCommand extends AbstractCommand<Credentials> {

    public SignUpCommand() {
        argument = new Argument<Credentials>(new Credentials("name", "pass"));
    }

    @Override
    public void handle() {
        System.out.println("handle sign up command");
    }
}
