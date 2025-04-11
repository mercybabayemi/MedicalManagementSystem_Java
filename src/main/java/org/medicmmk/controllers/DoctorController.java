package org.medicmmk.controllers;


import jakarta.validation.Valid;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.GetDoctorProfile;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.dtos.response.RegisterDoctorResponse;
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
    public ResponseEntity<RegisterDoctorResponse> register(@RequestBody RegisterDoctorRequest request) {
        String doctorId = doctorService.registerDoctorProfile(request).getId();
        RegisterDoctorResponse response = new RegisterDoctorResponse(doctorId, "Doctor registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Doctor> login(@Valid @RequestBody DoctorLoginRequest request){
        return ResponseEntity.ok(doctorService.findDoctorProfileByEmail(request.getEmail()));
    }

    @GetMapping("/doctor/email")
    public ResponseEntity<Doctor> getDoctorProfileByEmail(@Valid @RequestBody GetDoctorProfile request){
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
