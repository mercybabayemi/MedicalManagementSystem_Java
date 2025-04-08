package org.medicmmk.services;

import org.medicmmk.data.models.Doctor;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;

import java.util.List;

public interface DoctorService {
    Doctor loginDoctorToProfile(DoctorLoginRequest request);
    Doctor registerDoctorProfile(RegisterDoctorRequest request);
    Doctor findDoctorProfileByUsername(String username);
    List<Doctor> findALL();
    Doctor findDoctorProfileByEmail(String email);
    Doctor viewSchedule();
    Doctor searchForPatientByUsername(String username);
    Doctor searchForPatientByEmail(String email);
}
