package org.medicmmk.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorLoginRequest {
    @NOtBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
