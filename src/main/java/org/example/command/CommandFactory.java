package org.example.command;

import lombok.AllArgsConstructor;
import org.example.command.impl.*;
import org.example.exceptions.UnknownCommandException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class CommandFactory implements InitializingBean {

    private final AdminCommand adminCommand;
    private final AddCommand addCommand;
    private final RemoveCommand removeCommand;
    private final ViewAllCommand viewAllCommand;
    private final UserCommand userCommand;
    private final BrowseCommand browseCommand;
    private final BookCommand bookCommand;
    private final ViewCommand viewCommand;
    private final CancelCommand cancelCommand;
    private final ExitCommand exitCommand;

    private final Map<String, Command> commands = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        commands.put("admin", this.adminCommand);
        commands.put("add", this.addCommand);
        commands.put("remove", this.removeCommand);
        commands.put("view all", this.viewAllCommand);
        commands.put("user", this.userCommand);
        commands.put("browse", this.browseCommand);
        commands.put("book", this.bookCommand);
        commands.put("view", this.viewCommand);
        commands.put("cancel", this.cancelCommand);
        commands.put("exit", this.exitCommand);
    }

    public Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new UnknownCommandException("Command is unknown: " + commandName);
        }
        return command;
    }

}
