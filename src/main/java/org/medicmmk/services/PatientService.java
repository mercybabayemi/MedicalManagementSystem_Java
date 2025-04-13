package org.medicmmk.services;

import org.medicmmk.data.models.Patient;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;

public interface PatientService {

    Patient findPatient(String email);
    PatientLoginResponse login(PatientLoginRequest patientLoginRequest);
    void deleteAllPatients();
    PatientSignUpResponse signUp(PatientSignUpRequest patientSignUpRequest);
    long patientsCount();
    void bookAppointment(PatientAppointmentRequest appointmentRequest);
}