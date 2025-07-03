package org.example.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.enums.WorkSpaceType;

@Data
@AllArgsConstructor
public class WorkSpaceCreationDTO {

    @NotNull
    private WorkSpaceType type;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean available;

}
