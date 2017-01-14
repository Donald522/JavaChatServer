package model.command.factory;

import model.command.Command;
import model.command.impl.DefaultCommand;
import util.impl.AbstractFactory;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class CommandFactory extends AbstractFactory<Command> {
    public CommandFactory(Map<String, Command> commands) {
        super(commands);
        defaultValue = new DefaultCommand();
    }

    public CommandFactory(Map<String, Command> objects, Command defaultValue) {
        super(objects, defaultValue);
    }

}
