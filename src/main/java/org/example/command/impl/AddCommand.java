package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.service.WorkSpaceService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class AddCommand implements Command {

    private final WorkSpaceService workSpaceService;
    private final Scanner scanner = new Scanner(System.in);

    private static final Map<Long, WorkSpaceType> types = Map.of(
            1L, WorkSpaceType.FLEXIBLE_DESK,
            2L, WorkSpaceType.PRIVATE_ROOM,
            3L, WorkSpaceType.CONFERENCE_ROOM
    );

    @Override
    public void execute() {
        WorkSpace workSpace = new WorkSpace();

        System.out.println("\nWhat is the type of the new work space?\n1. Desk\n2. Private room\n3. Conference room");
        System.out.println("Enter numbers 1, 2 or 3:");
        workSpace.setType(types.get(Long.parseLong(scanner.nextLine())));

        System.out.println("What is the price of the work space? (enter numeric value)");
        workSpace.setPrice(Integer.parseInt(scanner.nextLine()));

        workSpaceService.save(workSpace);

        System.out.println("\nWorkspace was saved successfully.\nHit enter to continue...");
        scanner.nextLine();
    }

}
