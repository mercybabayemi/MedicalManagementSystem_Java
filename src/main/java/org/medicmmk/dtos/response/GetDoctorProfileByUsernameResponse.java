package org.medicmmk.dtos.response;

import lombok.Getter;
import lombok.Setter;
import org.medicmmk.data.models.Doctor;

@Setter
@Getter
public class GetDoctorProfileByUsernameResponse {
    private Doctor doctor;

    public GetDoctorProfileByUsernameResponse(Doctor newDoctor){
        this.doctor = newDoctor;
    }
}
