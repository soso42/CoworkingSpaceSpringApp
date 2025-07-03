package org.example.repository.impl;

import jakarta.transaction.Transactional;
import org.example.model.entity.Booking;
import org.example.model.entity.WorkSpace;
import org.example.model.enums.WorkSpaceType;
import org.example.repository.BookingRepository;
import org.example.repository.WorkSpaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WorkSpaceRepository workSpaceRepository;


    @Test
    void save_happyPath() {
        // Given
        WorkSpace workSpace = getWorkSpaceFromDB();
        Booking booking = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();

        // When
        Booking savedBooking = bookingRepository.save(booking);

        // Then
        assertAll(
                () -> assertEquals(booking.getWorkSpace().getId(), savedBooking.getWorkSpace().getId()),
                () -> assertEquals(booking.getStartDate(), savedBooking.getStartDate()),
                () -> assertEquals(booking.getEndDate(), savedBooking.getEndDate())
        );
    }

    @Test
    void findById_happyPath() {
        // Given
        WorkSpace workSpace = getWorkSpaceFromDB();
        Booking booking = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        // When
        Optional<Booking> foundBooking = bookingRepository.findById(savedBooking.getId());

        // Then
        assertTrue(foundBooking.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
    void findById_whenIdNotExists_returnsEmptyOptional(Long id) {
        // Given
        // When
        Optional<Booking> optBooking = bookingRepository.findById(id);

        // Then
        assertTrue(optBooking.isEmpty());
    }

    @Test
    void findAll_happyPath() {
        // Given
        int initialSize = bookingRepository.findAll().size();
        WorkSpace workSpace = getWorkSpaceFromDB();
        Booking booking = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.of(2027, 5,5))
                .endDate(LocalDate.of(2027, 6, 6))
                .build();
        bookingRepository.save(booking);

        // When
        int actualSize = bookingRepository.findAll().size();

        // Then
        assertEquals(initialSize + 1, actualSize);
    }

    @Test
    void delete_happyPath() {
        // Given
        WorkSpace workSpace = getWorkSpaceFromDB();
        Booking booking = Booking.builder()
                .workSpace(workSpace)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        // When
        bookingRepository.delete(savedBooking);
        Optional<Booking> foundBooking = bookingRepository.findById(savedBooking.getId());

        // Then
        assertTrue(foundBooking.isEmpty());
    }




    private WorkSpace getWorkSpaceFromDB() {
        WorkSpace workSpace = WorkSpace.builder()
                .type(WorkSpaceType.CONFERENCE_ROOM)
                .price(132)
                .available(true)
                .build();
        return workSpaceRepository.save(workSpace);
    }

}
