package org.example.command.impl;

import lombok.AllArgsConstructor;
import org.example.command.Command;
import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.service.BookingService;
import org.example.service.WorkSpaceService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class BookCommand implements Command {

    private final BookingService bookingService;
    private final WorkSpaceService workSpaceService;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

        System.out.println("\nList of Co-working spaces:\n");
        workSpaceService.findAll().forEach(System.out::println);

        System.out.println("\nPlease enter ID of desired Co-working space:");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.println("\nPlease enter START date: (format: YYYY-MM-DD)");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("\nPlease enter END date: (format: YYYY-MM-DD)");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Booking booking = new Booking(null, null, startDate, endDate);
        try {
            bookingService.book(booking);
            System.out.println("\nBooking successfully made");
        } catch (BookingNotAvailableException e) {
            System.out.println("\nBooking not available for your chosen dates...");
        }

        System.out.println("\nEnter to continue...");
        scanner.nextLine();
    }

}
