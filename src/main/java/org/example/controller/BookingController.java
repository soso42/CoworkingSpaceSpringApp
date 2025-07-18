package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.dto.booking.BookingDTO;
import org.example.model.dto.booking.BookingUpdateDTO;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBooking(@RequestBody @Validated BookingCreationDTO dto) {
        bookingService.book(dto);
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.findAllDTO();
    }

    @PatchMapping("/{id}")
    public BookingDTO updateBooking(@RequestBody BookingUpdateDTO dto, @PathVariable("id") Long id) {
        return bookingService.updateBooking(id, dto);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable("id") Long id) {
        bookingService.cancelBooking(id);
    }


    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> handleBookingNotFoundException(BookingNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingNotAvailableException.class)
    public ResponseEntity<?> handleBookingNotAvailableException(BookingNotAvailableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}
