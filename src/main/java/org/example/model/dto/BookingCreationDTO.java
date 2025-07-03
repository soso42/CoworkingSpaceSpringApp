package org.example.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingCreationDTO {

    private Long workSpaceId;
    private LocalDate startDate;
    private LocalDate endDate;

}
