package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkSpace workSpace;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


    public static BookingBuilder builder() {
        return new BookingBuilder();
    }

    public static class BookingBuilder {

        Booking booking = new Booking();

        public BookingBuilder id(Long id) {
            booking.id = id;
            return this;
        }

        public BookingBuilder workSpace(WorkSpace workSpace) {
            booking.workSpace = workSpace;
            return this;
        }

        public BookingBuilder startDate(LocalDate startDate) {
            booking.startDate = startDate;
            return this;
        }

        public BookingBuilder endDate(LocalDate endDate) {
            booking.endDate = endDate;
            return this;
        }

        public Booking build() {
            return booking;
        }
    }

}
