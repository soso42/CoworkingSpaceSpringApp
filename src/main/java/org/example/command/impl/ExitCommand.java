package org.example.command.impl;

import org.example.command.Command;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }

}
