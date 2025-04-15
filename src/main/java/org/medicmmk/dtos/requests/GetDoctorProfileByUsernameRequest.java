package org.medicmmk.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetDoctorProfileByUsernameRequest {
    @NotBlank(message = "Username is required")
    private String username;
}
