package org.example.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingCreationDTO {

    @NotNull
    private Long workSpaceId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
