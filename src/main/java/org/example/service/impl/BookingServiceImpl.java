package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.entity.Booking;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.repository.BookingRepository;
import org.example.service.BookingService;
import org.example.service.WorkSpaceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final WorkSpaceService workSpaceService;
    private final ModelMapper modelMapper;


    @Override
    public void book(BookingCreationDTO dto) {
        Booking booking = modelMapper.map(dto, Booking.class);
        booking.setId(null);
        WorkSpace workSpace = workSpaceService.findById(dto.getWorkSpaceId())
                .orElseThrow(() -> new WorkSpaceNotFoundException("WorkSpace with the id " + dto.getWorkSpaceId() + " does not exist"));
        booking.setWorkSpace(workSpace);

        if (bookingOverlapsOthers(booking)) {
            throw new BookingNotAvailableException("Booking overlaps another booking");
        }
        bookingRepository.save(booking);
    }

    private boolean bookingOverlapsOthers(Booking booking) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getWorkSpace().getId().equals(booking.getWorkSpace().getId()))
                .anyMatch(b -> overlaps(b, booking));
    }

    private boolean overlaps(Booking b1, Booking b2) {
        return !b1.getStartDate().isAfter(b2.getEndDate()) && !b1.getEndDate().isBefore(b2.getStartDate());
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void cancelBooking(Long id) {
        Optional<Booking> optBooking = this.bookingRepository.findById(id);
        if (optBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking not found");
        }
        this.bookingRepository.delete(optBooking.get());
    }

}
