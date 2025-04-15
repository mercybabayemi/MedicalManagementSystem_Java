package org.medicmmk.services;

import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Schedule;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByEmailRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByUsernameRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;

import java.util.List;

public interface DoctorService {
    Doctor loginDoctor(DoctorLoginRequest request);
    Doctor registerDoctor(RegisterDoctorRequest request);
    Doctor findDoctorByUsername(GetDoctorProfileByUsernameRequest request);
    Doctor findDoctorByEmail(GetDoctorProfileByEmailRequest request);
    List<Doctor> findAllDoctors();
    Schedule viewDoctorSchedule(String doctorId);
    Doctor updateDoctorAvailability(String doctorId, boolean isOnCall);
}


