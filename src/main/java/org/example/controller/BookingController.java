package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.dto.booking.BookingDTO;
import org.example.model.dto.booking.BookingUpdateDTO;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping("")
    public ResponseEntity<?> createBooking(@RequestBody @Validated BookingCreationDTO dto) {
        try {
            bookingService.book(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (WorkSpaceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", "Workspace not found"), HttpStatus.BAD_REQUEST);
        } catch (BookingNotAvailableException e) {
            return new ResponseEntity<>(Map.of("error", "Booking not available"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getAllBookings() {
        return bookingService.findAllDTO();
    }

    @PutMapping("")
    public ResponseEntity<?> updateBooking(@RequestBody @Validated BookingUpdateDTO dto) {
        try {
            BookingDTO booking = bookingService.updateBooking(dto);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (BookingNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", "Booking not found"), HttpStatus.BAD_REQUEST);
        } catch (WorkSpaceNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", "Workspace not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable("id") Long id) {
        try {
            bookingService.cancelBooking(id);
        } catch (BookingNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
    }


}
