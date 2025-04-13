package org.medicmmk.services;

import org.medicmmk.data.models.Patient;
import org.medicmmk.data.repository.PatientRepository;
import org.medicmmk.exceptions.DuplicatePatientException;
import org.medicmmk.exceptions.InvalidEmailException;
import org.medicmmk.exceptions.InvalidPasswordException;
import org.medicmmk.exceptions.PatientDoesNotExistException;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServicesImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;


    @Override
    public Patient findPatient(String email) {
        return null;
    }



    @Override
    public PatientLoginResponse login(PatientLoginRequest patientLoginRequest) {
        validateEmail(patientLoginRequest.getEmail());
        Patient patient = patientRepository.findByEmail(patientLoginRequest.getEmail());
        ValidatePatient(patient);
        String password = patientLoginRequest.getPassword();
        validatePassword(password,patient);
        PatientLoginResponse patientLoginResponse = new PatientLoginResponse();
        patientLoginResponse.setPatientLoginResponse("Login Successful");
        return patientLoginResponse;
    }

    private void ValidatePatient(Patient patient) {
        if (patient == null) throw new PatientDoesNotExistException("Patient Does Not Exist");
    }

    private void validateEmail(String email) {
        if (email == null) throw new InvalidEmailException("Invalid Credentials ");
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

    @Override
    public void bookAppointment(PatientAppointmentRequest patientAppointmentRequest) {


    }


}