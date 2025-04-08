package org.medicmmk.services;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Patient;
import org.medicmmk.data.repository.PatientRepository;
import org.medicmmk.exceptions.DuplicatePatientException;
import org.medicmmk.exceptions.InvalidPasswordException;
import org.medicmmk.exceptions.PatientDoesNotExistException;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PatientServicesImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;


    @Override
    public Patient findPatient(String email) {
        return null;
    }

    @Override
    public Patient findByUsername(String userName) {
        return null;
    }

    @Override
    public PatientLoginResponse login(PatientLoginRequest patientLoginRequest) {

        Patient patient = patientRepository.findByEmail(patientLoginRequest.getEmail());
        if (patient == null) throw new PatientDoesNotExistException("Patient Does Not Exist");
        String password = patientLoginRequest.getPassword();
        validatePassword(password,patient);
        PatientLoginResponse patientLoginResponse = new PatientLoginResponse();
        patientLoginResponse.setPatientLoginResponse("Login Successful");
        return patientLoginResponse;
    }

    private void validatePassword(String password, Patient patient) {
        if (!patient.getPassword().equals(password)) throw new InvalidPasswordException("Invalid Credentials");
    }

    @Override
    public void deleteAllPatients() {
        patientRepository.deleteAll();
    }

    @Override
    public PatientSignUpResponse signUp(PatientSignUpRequest patientSignUpRequest) {
        ValidatePatientExistence(patientSignUpRequest);
        Patient patient = new Patient();
        patient.setFirstName(patientSignUpRequest.getFirstName());
        patient.setLastName(patientSignUpRequest.getLastName());
        patient.setEmail(patientSignUpRequest.getEmail());
        patient.setPassword(patientSignUpRequest.getPassword());
        patientRepository.save(patient);
        PatientSignUpResponse patientSignUpResponse = new PatientSignUpResponse();
        patientSignUpResponse.setSignUpResponse("SignUp Successful");
        return patientSignUpResponse;
    }
    private void ValidatePatientExistence (PatientSignUpRequest patientSignUpRequest) {
        if(patientRepository.findByEmail(patientSignUpRequest.getEmail())!= null) throw new DuplicatePatientException("Patient Already Exist");
    }

    @Override
    public long patientsCount() {
        return patientRepository.count();
    }

}