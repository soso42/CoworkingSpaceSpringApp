package org.example;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.command.CommandFactory;
import org.example.enums.AccessLevel;
import org.example.exceptions.UnknownCommandException;
import org.example.service.AuthService;
import org.example.service.impl.AppStateServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class AppFacade {

    private final AuthService authService;
    private final CommandFactory commandFactory;
    private final Scanner scanner = new Scanner(System.in);

    private final Map<AccessLevel, Runnable> menus = Map.of(
            AccessLevel.ADMIN, this::displayAdminMenu,
            AccessLevel.USER, this::displayUserMenu,
            AccessLevel.NONE, this::displayMainMenu
    );

    public void start() {

        new AppStateServiceImpl().initDatabase();

        while (true) {
            AccessLevel accessLevel = authService.getAccessLevel();
            menus.get(accessLevel).run();

            String input = scanner.nextLine();

            try {
                Command command = commandFactory.getCommand(input);
                command.execute();
            } catch (UnknownCommandException e) {
                System.out.println("\n!!!!!     UNKNOWN COMMAND. Please try again     !!!!!");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("""
                
                Welcome to the main menu!

                list of available commands:

                admin  - logs you in as ADMIN
                user   - logs you in as USER
                exit   - exits the program

                Please type a command to continue:""");
    }

    private void displayAdminMenu() {
        System.out.println("""
                
                Welcome to the ADMIN menu!

                list of available commands:

                add       - adds a new coworking space
                remove    - removes a coworking space
                view all  - view all reservations
                exit      - exits the program

                Please type a command to continue:""");
    }

    private void displayUserMenu() {
        System.out.println("""

                Welcome to the USER menu!

                list of available commands:

                browse    - browse available coworking spaces
                book      - make a reservation
                view      - view my reservations
                cancel    - cancel a reservations
                exit      - exits the program

                Please type a command to continue:""");
    }

}
