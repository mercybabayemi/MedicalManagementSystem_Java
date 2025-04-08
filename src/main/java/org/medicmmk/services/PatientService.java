package org.medicmmk.services;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Patient;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PatientService {

    Patient findPatient(String email);
    Patient findByUsername(String userName);
    PatientLoginResponse login(PatientLoginRequest patientLoginRequest);
    void deleteAllPatients();
    PatientSignUpResponse signUp(PatientSignUpRequest patientSignUpRequest);
    long patientsCount();

}