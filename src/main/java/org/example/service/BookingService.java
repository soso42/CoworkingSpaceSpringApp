package org.example.service;

import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.dto.booking.BookingDTO;
import org.example.model.dto.booking.BookingUpdateDTO;
import org.example.model.entity.Booking;

import java.util.List;

public interface BookingService {
    void book(BookingCreationDTO dto);
    List<Booking> findAll();
    List<BookingDTO> findAllDTO();
    BookingDTO updateBooking(Long id, BookingUpdateDTO booking);
    void cancelBooking(Long id);
}
