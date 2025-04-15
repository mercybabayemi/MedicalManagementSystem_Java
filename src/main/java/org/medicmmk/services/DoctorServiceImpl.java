package org.medicmmk.services;

import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Schedule;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByEmailRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByUsernameRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.exceptions.DoctorNotFoundException;
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
    public Doctor loginDoctor(DoctorLoginRequest request) {
        Doctor foundUser = doctorRepository.findByEmail(request.getEmail()).orElse(null);
        if (foundUser == null) { throw new DoctorNotFoundException("Doctor not found");}
        if (!foundUser.getEmail().equals(request.getEmail())) throw new InvalidEmailInputException("Invalid Email");
        if (!foundUser.getPassword().equals(request.getPassword())) throw new IncorrectPasswordException("Wrong Password");
        return foundUser;
    }

    @Override
    public Doctor registerDoctor(RegisterDoctorRequest request) {
        return doctorRepository.save(request.getDoctor());
    }

    public Doctor findDoctorByUsername(GetDoctorProfileByUsernameRequest request){
       return doctorRepository.findByUsername(request.getUsername()).orElse(null);
    }


    public Doctor findDoctorByEmail(GetDoctorProfileByEmailRequest request){
        return doctorRepository.findByEmail(request.getEmail()).orElse(null);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Schedule viewDoctorSchedule(String doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found")).getSchedule();
    }

    @Override
    public Doctor updateDoctorAvailability(String doctorId, boolean isOnCall) {
        Doctor foundDoctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        foundDoctor.setOnCall(isOnCall);
        return doctorRepository.save(foundDoctor);
    }

}
