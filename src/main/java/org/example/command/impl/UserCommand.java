package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.enums.AccessLevel;
import org.example.service.AuthService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCommand implements Command {

    private final AuthService authService;

    @Override
    public void execute() {
        this.authService.setAccessLevel(AccessLevel.USER);
    }

}
