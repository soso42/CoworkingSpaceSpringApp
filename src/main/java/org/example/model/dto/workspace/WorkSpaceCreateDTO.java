package org.example.model.dto.workspace;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.WorkSpaceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceCreateDTO {

    @NotNull
    private WorkSpaceType type;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean available;

}
