package model.command.factory;

import model.command.Command;
import util.impl.AbstractFactory;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class CommandFactory extends AbstractFactory<Command> {
    public CommandFactory(Map<String, Command> commands) {
        super(commands);
    }
}
