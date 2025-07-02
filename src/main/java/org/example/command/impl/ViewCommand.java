package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.service.BookingService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@AllArgsConstructor
public class ViewCommand implements Command {

    private final BookingService bookingService;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

        System.out.println("\nList of all my reservations\n");

        bookingService.findAll().forEach(System.out::println);

        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

}
