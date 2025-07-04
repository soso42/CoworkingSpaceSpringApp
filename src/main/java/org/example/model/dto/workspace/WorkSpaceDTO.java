package org.example.model.dto.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.WorkSpaceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceDTO {

    private Long id;
    private WorkSpaceType type;
    private Integer price;
    private Boolean available;

}
