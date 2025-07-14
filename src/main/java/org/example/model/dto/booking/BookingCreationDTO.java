package org.example.model.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreationDTO {

    @NotNull
    private Long workSpaceId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
