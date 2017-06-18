package commands.factory;


import commands.command.Command;
import commands.command.impl.DefaultCommand;
import util.JsonNodes;
import util.impl.AbstractFactory;

import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 11.01.17
 */

public class CommandFactory extends AbstractFactory<Command<?>> {
    public CommandFactory(Map<String, Command<?>> commands) {
        super(commands);
        defaultValue = new DefaultCommand();
    }

    public CommandFactory(Map<String, Command<?>> objects, Command defaultValue) {
        super(objects, defaultValue);
    }

    @Override
    protected Command get(Map<JsonNodes, ?> nodesMap) {
        Command<?> command = objects.get((String) nodesMap.get(JsonNodes.COMMAND));
        return command.withArguments(nodesMap);
    }

}
