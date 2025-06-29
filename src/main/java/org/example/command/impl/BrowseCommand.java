package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.service.WorkSpaceService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@AllArgsConstructor
public class BrowseCommand implements Command {

    private final WorkSpaceService workSpaceService;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("\nHere is the list of available Coworking Spaces:\n");

        workSpaceService.findAll().stream()
                .filter(s -> s.getAvailable() == true)
                .forEach(System.out::println);

        System.out.println("\nHit enter to continue...");
        scanner.nextLine();
    }

}
