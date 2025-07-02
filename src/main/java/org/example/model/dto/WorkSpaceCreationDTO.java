package org.example.model.dto;

import lombok.Data;
import org.example.model.enums.WorkSpaceType;

@Data
public class WorkSpaceCreationDTO {

    private WorkSpaceType type;
    private Integer price;
    private Boolean available;

}
