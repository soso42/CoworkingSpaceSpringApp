package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.dto.booking.BookingDTO;
import org.example.model.dto.booking.BookingUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;

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
    public List<BookingDTO> findAllDTO() {
        return findAll().stream()
                .map(this::mapBookingToDTO)
                .toList();
    }

    private BookingDTO mapBookingToDTO(Booking booking) {
        BookingDTO dto = modelMapper.map(booking, BookingDTO.class);
        dto.setWorkSpaceId(booking.getWorkSpace().getId());
        return dto;
    }


    @Transactional
    @Override
    public BookingDTO updateBooking(Long id, BookingUpdateDTO dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking with the id " + id + " does not exist"));

        if (dto.getWorkSpaceId() != null) {
            WorkSpace workSpace = workSpaceService.findById(dto.getWorkSpaceId())
                    .orElseThrow(() -> new WorkSpaceNotFoundException("WorkSpace with the id " + dto.getWorkSpaceId() + " does not exist"));
            booking.setWorkSpace(workSpace);
        }

        Optional.ofNullable(dto.getStartDate()).ifPresent(booking::setStartDate);
        Optional.ofNullable(dto.getEndDate()).ifPresent(booking::setEndDate);

        bookingRepository.save(booking);
        return mapBookingToDTO(booking);
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
