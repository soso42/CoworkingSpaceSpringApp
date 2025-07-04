package org.example.model.dto.workspace;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.WorkSpaceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private WorkSpaceType type;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean available;

}
