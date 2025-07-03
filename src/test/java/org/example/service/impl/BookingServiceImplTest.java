package org.example.service.impl;

import org.example.model.dto.BookingCreationDTO;
import org.example.model.entity.Booking;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;
import org.example.repository.WorkSpaceRepository;
import org.example.service.BookingService;
import org.example.service.WorkSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private WorkSpaceRepository workSpaceRepository;

    private WorkSpaceService workSpaceService;

    @Autowired
    private ModelMapper modelMapper;

    private BookingService bookingService;


    @BeforeEach
    void setUp() {
        this.workSpaceService = new WorkSpaceServiceImpl(workSpaceRepository, modelMapper);
        this.bookingService = new BookingServiceImpl(bookingRepository, workSpaceService, modelMapper);
    }


    @Test
    void book_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .id(1L).build();
        Booking bookingInDb = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        when(bookingRepository.findAll()).thenReturn(List.of(bookingInDb));
        when(workSpaceRepository.findById(anyLong())).thenReturn(Optional.of(workSpace));
        BookingCreationDTO dto = new BookingCreationDTO();
        dto.setWorkSpaceId(workSpace.getId());
        dto.setStartDate(LocalDate.parse("2026-01-01"));
        dto.setEndDate(LocalDate.parse("2026-02-02"));

        // When
        // Then
        assertDoesNotThrow(() -> bookingService.book(dto));
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void book_whenBookingsOverlap_throwException() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .id(1L).build();
        Booking bookingInDb = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        when(bookingRepository.findAll()).thenReturn(List.of(bookingInDb));
        when(workSpaceRepository.findById(anyLong())).thenReturn(Optional.of(workSpace));
        BookingCreationDTO dto = new BookingCreationDTO();
        dto.setWorkSpaceId(workSpace.getId());
        dto.setStartDate(LocalDate.parse("2027-01-01"));
        dto.setEndDate(LocalDate.parse("2027-02-02"));

        // When
        // Then
        assertThrows(BookingNotAvailableException.class, () -> bookingService.book(dto));
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void findAll() {
        // Given
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        int result = bookingService.findAll().size();

        // Then
        assertEquals(bookings.size(), result);
    }

    @Test
    void cancelBooking_happyPath() {
        // Given
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));

        // When
        // Then
        assertDoesNotThrow(() -> bookingService.cancelBooking(111L));
    }

    @Test
    void cancelBooking_whenIdIncorrect() {
        // Given
        Long mockId = Long.MAX_VALUE;
        when(bookingRepository.findById(mockId)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBooking(mockId));
    }

}
