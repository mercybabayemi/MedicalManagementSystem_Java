package org.medicmmk.controllers;


import org.medicmmk.data.models.Doctor;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.GetDoctorProfile;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.services.DoctorService;
import org.medicmmk.services.DoctorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorServiceImpl doctorProfileService) {
        this.doctorService = doctorProfileService;
    }

    @PostMapping("/register")
    public ResponseEntity<Doctor> register(@RequestBody RegisterDoctorRequest request) {
        return ResponseEntity.ok(doctorService.registerDoctorProfile(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Doctor> login(@RequestBody DoctorLoginRequest request){
        return ResponseEntity.ok(doctorService.findDoctorProfileByEmail(request.getEmail()));
    }

    @GetMapping("/doctor/email")
    public ResponseEntity<Doctor> getDoctorProfileByEmail(@RequestBody GetDoctorProfile request){
        return ResponseEntity.ok(doctorService.findDoctorProfileByEmail(request.getEmail()));
    }

    @GetMapping("/doctor/username")
    public ResponseEntity<Doctor> getDoctorProfileByUsername(@RequestBody GetDoctorProfile request){
        return ResponseEntity.ok(doctorService.findDoctorProfileByUsername(request.getUsername()));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllUsers(){
        return ResponseEntity.ok(doctorService.findALL());
    }
}
