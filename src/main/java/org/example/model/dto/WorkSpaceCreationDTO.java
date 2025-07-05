package org.example.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.model.enums.WorkSpaceType;

@Data
public class WorkSpaceCreationDTO {

    @NotNull
    private WorkSpaceType type;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean available;

}
