package org.medicmmk.services;

import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.exceptions.IncorrectPasswordException;
import org.medicmmk.exceptions.InvalidEmailInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor loginDoctorToProfile(DoctorLoginRequest request) {
        Doctor foundUser = doctorRepository.findByEmail(request.getEmail()).orElse(null);
        assert foundUser != null;
        if (!foundUser.getEmail().equals(request.getEmail())) throw new InvalidEmailInputException("Invalid Email");
        if (!foundUser.getPassword().equals(request.getPassword())) throw new IncorrectPasswordException("Wrong Password");
        return foundUser;
    }

    @Override
    public Doctor registerDoctorProfile(RegisterDoctorRequest request) {
        return doctorRepository.save(request.getDoctor());
    }

    public Doctor findDoctorProfileByUsername(String username){
        return doctorRepository.findByUsername(username).orElse(null);
    }


    public Doctor findDoctorProfileByEmail(String email){
        return doctorRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Doctor viewSchedule() {
        return null;
    }

    @Override
    public Doctor searchForPatientByUsername(String username) {
        return null;
    }

    @Override
    public Doctor searchForPatientByEmail(String email) {
        return null;
    }

    public List<Doctor> findALL(){
        return doctorRepository.findAll();
    }
}
