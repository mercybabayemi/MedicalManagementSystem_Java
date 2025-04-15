package org.medicmmk.dtos.response;

import lombok.Getter;
import lombok.Setter;
import org.medicmmk.data.models.Doctor;

@Setter
@Getter
public class GetDoctorProfileByEmailResponse {
    private Doctor doctor;

    public GetDoctorProfileByEmailResponse(Doctor newDoctor){
        this.doctor = newDoctor;
    }
}
