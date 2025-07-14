package org.example.model.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
