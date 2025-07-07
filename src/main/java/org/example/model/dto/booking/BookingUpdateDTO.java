package org.example.model.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateDTO {

    private Long workSpaceId;

    private LocalDate startDate;

    private LocalDate endDate;

}
