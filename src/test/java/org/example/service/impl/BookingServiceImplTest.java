package org.example.service.impl;

import org.example.model.dto.booking.BookingCreationDTO;
import org.example.model.dto.booking.BookingDTO;
import org.example.model.dto.booking.BookingUpdateDTO;
import org.example.model.entity.Booking;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.BookingNotAvailableException;
import org.example.model.exceptions.BookingNotFoundException;
import org.example.model.exceptions.WorkSpaceNotFoundException;
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
    void findAll_happyPath() {
        // Given
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        int result = bookingService.findAll().size();

        // Then
        assertEquals(bookings.size(), result);
    }

    @Test
    void findAllDto_happyPath() {
        // Given
        List<Booking> bookings = List.of(
                new Booking(1L, new WorkSpace(), LocalDate.parse("2027-01-01"), LocalDate.parse("2027-02-01")),
                new Booking(2L, new WorkSpace(), LocalDate.parse("2027-01-01"), LocalDate.parse("2027-02-01"))
        );
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        int result = bookingService.findAllDTO().size();

        // Then
        assertEquals(bookings.size(), result);
    }

    @Test
    public void updateBooking_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .id(22L)
                .build();
        Booking savedBooking = Booking.builder()
                .id(1L)
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        BookingUpdateDTO dto = new BookingUpdateDTO();
        dto.setWorkSpaceId(workSpace.getId());
        dto.setStartDate(LocalDate.parse("2028-01-01"));
        dto.setEndDate(LocalDate.parse("2028-02-02"));

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(savedBooking));
        when(workSpaceRepository.findById(anyLong())).thenReturn(Optional.of(workSpace));

        // When
        BookingDTO result = bookingService.updateBooking(1L, dto);

        // Then
        assertAll(
                () -> assertEquals(result.getWorkSpaceId(), dto.getWorkSpaceId()),
                () -> assertEquals(result.getStartDate(), dto.getStartDate()),
                () -> assertEquals(result.getEndDate(), dto.getEndDate())
        );
    }

    @Test
    public void updateBooking_whenBookingIdNotFound_throwsException() {
        // Given
        BookingUpdateDTO dto = new BookingUpdateDTO();
        dto.setWorkSpaceId(22L);
        dto.setStartDate(LocalDate.parse("2027-01-01"));
        dto.setEndDate(LocalDate.parse("2027-02-02"));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(BookingNotFoundException.class, () -> bookingService.updateBooking(22L, dto));
    }

    @Test
    public void updateBooking_whenWorkSpaceIdNotFound_throwsException() {
        // Given
        BookingUpdateDTO dto = new BookingUpdateDTO();
        dto.setWorkSpaceId(22L);
        dto.setStartDate(LocalDate.parse("2027-01-01"));
        dto.setEndDate(LocalDate.parse("2027-02-02"));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));

        // When
        // Then
        assertThrows(WorkSpaceNotFoundException.class, () -> bookingService.updateBooking(22L, dto));
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
