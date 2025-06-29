package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.exceptions.BookingNotFoundException;
import org.example.service.BookingService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@AllArgsConstructor
public class CancelCommand implements Command {

    private final BookingService bookingService;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

        System.out.println("\nAvailable bookings: \n");

        bookingService.findAll().forEach(System.out::println);

        System.out.println("\nPlease enter the booking id you want to cancel: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            bookingService.cancelBooking(id);
            System.out.println("\nBooking cancelled successfully.\n");
        } catch (BookingNotFoundException e) {
            System.out.println("\nBooking with the id " + id + " was not found.\n");
        }

        System.out.println("Hit enter to continue...");
        scanner.nextLine();
    }

}
