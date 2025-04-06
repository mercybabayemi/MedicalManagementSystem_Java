package org.medicmmk.controllers;

import jakarta.validation.Valid;
import org.medicmmk.services.PatientServicesImpl;
import org.medicmmk.services.dtos.requests.PatientLoginRequest;
import org.medicmmk.services.dtos.requests.PatientSignUpRequest;
import org.medicmmk.services.dtos.response.PatientLoginResponse;
import org.medicmmk.services.dtos.response.PatientSignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientServicesImpl patientServices;

    @PostMapping("/Signup")
    public PatientSignUpResponse signUpPatient(@Valid @RequestBody PatientSignUpRequest patientSignUpRequest){
        return patientServices.signUp(patientSignUpRequest);
    }

    @PostMapping("/login")
    public PatientLoginResponse loginPatient(@RequestBody PatientLoginRequest patientLoginRequest){
        return patientServices.login(patientLoginRequest);
    }





}