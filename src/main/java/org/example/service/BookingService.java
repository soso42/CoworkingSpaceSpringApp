package org.example.service;

import org.example.model.dto.BookingCreationDTO;
import org.example.model.entity.Booking;

import java.util.List;

public interface BookingService {
    void book(BookingCreationDTO dto);
    List<Booking> findAll();
    void cancelBooking(Long id);
}
