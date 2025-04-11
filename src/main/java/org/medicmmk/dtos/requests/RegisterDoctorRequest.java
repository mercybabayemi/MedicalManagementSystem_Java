package org.medicmmk.dtos.requests;
import lombok.Getter;
import lombok.Setter;
import org.medicmmk.data.models.Doctor;

@Setter
@Getter
public class RegisterDoctorRequest {
    @Valid
    private Doctor doctor;
}
