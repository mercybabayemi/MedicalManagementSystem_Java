package org.medicmmk.controllers;

import org.medicmmk.services.PatientServicesImpl;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;
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
    public PatientSignUpResponse signUp( @RequestBody PatientSignUpRequest patientSignUpRequest){
        return patientServices.signUp(patientSignUpRequest);
    }

    @PostMapping("/login")
    public PatientLoginResponse login(@RequestBody PatientLoginRequest patientLoginRequest){
        return patientServices.login(patientLoginRequest);
    }






}