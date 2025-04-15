package org.medicmmk.controllers;


import jakarta.validation.Valid;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Schedule;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByEmailRequest;
import org.medicmmk.dtos.requests.GetDoctorProfileByUsernameRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.dtos.response.DoctorLoginResponse;
import org.medicmmk.dtos.response.GetDoctorProfileByEmailResponse;
import org.medicmmk.dtos.response.GetDoctorProfileByUsernameResponse;
import org.medicmmk.dtos.response.RegisterDoctorResponse;
import org.medicmmk.services.DoctorService;
import org.medicmmk.services.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorServiceImpl doctorProfileService) {
        this.doctorService = doctorProfileService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDoctorResponse> register(@RequestBody RegisterDoctorRequest request) {
        Doctor doctor = doctorService.registerDoctor(request);
        return ResponseEntity.ok(new RegisterDoctorResponse(doctor.getId(), "Doctor registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<DoctorLoginResponse> login(@Valid @RequestBody DoctorLoginRequest request){
        Doctor doctor = doctorService.loginDoctor(request);
        return ResponseEntity.ok(new DoctorLoginResponse(doctor.getId(), "Doctor logged in successfully"));
    }

    @GetMapping("/doctor/email")
    public ResponseEntity<GetDoctorProfileByEmailResponse> getDoctorProfileByEmail(@Valid @RequestBody GetDoctorProfileByEmailRequest request){
        Doctor doctor = doctorService.findDoctorByEmail(request);
        return ResponseEntity.ok(new GetDoctorProfileByEmailResponse(doctor));
    }

    @GetMapping("/doctor/username")
    public ResponseEntity<GetDoctorProfileByUsernameResponse> getDoctorProfileByUsername(@Valid @RequestBody GetDoctorProfileByUsernameRequest request){
        Doctor doctor = doctorService.findDoctorByUsername(request);
        return ResponseEntity.ok(new GetDoctorProfileByUsernameResponse(doctor));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllUsers(){
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @PutMapping("/{doctorId}/availability")
    public ResponseEntity<Doctor> updateAvailability(@PathVariable String doctorId, @RequestParam boolean isOnCall){
        Doctor updateDoctor = doctorService.updateDoctorAvailability(doctorId, isOnCall);
        return ResponseEntity.ok(updateDoctor);
    }

    @GetMapping("/{doctorId}/schedule")
    public ResponseEntity<Schedule> viewSchedule(@PathVariable String doctorId){
        return ResponseEntity.ok(doctorService.viewDoctorSchedule(doctorId));
    }
}
