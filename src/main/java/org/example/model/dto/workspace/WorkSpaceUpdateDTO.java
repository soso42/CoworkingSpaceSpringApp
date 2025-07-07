package org.example.model.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.WorkSpaceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceUpdateDTO {

    private WorkSpaceType type;

    private Integer price;

    private Boolean available;

}
