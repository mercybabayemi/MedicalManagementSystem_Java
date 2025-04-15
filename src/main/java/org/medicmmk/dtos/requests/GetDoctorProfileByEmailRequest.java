package org.medicmmk.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetDoctorProfileByEmailRequest {
    @NotBlank(message = "Email is required")
    private String email;
}
