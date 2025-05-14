package org.medicmmk.controllers;

import lombok.RequiredArgsConstructor;
import org.medicmmk.data.models.Doctor; // Required for updated registerDoctor method signature if Doctor was not previously imported directly
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.dtos.response.AuthenticationResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;
import org.medicmmk.dtos.response.RegisterDoctorResponse;
import org.medicmmk.security.JwtUtil;
import org.medicmmk.services.DoctorService;
import org.medicmmk.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService; // Used to fetch UserDetails for token generation
    private final JwtUtil jwtUtil;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/patient")
    public ResponseEntity<PatientSignUpResponse> registerPatient(@RequestBody PatientSignUpRequest request) {
        // Encode password before passing to service
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        PatientSignUpResponse response = patientService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<RegisterDoctorResponse> registerDoctor(@RequestBody RegisterDoctorRequest request) {
        // Encode password before passing to service
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Doctor registeredDoctor = doctorService.registerDoctor(request); // Service returns Doctor entity
        // Create response DTO using the ID from the registered Doctor
        RegisterDoctorResponse response = new RegisterDoctorResponse(registeredDoctor.getId(), "Doctor registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/patient")
    public ResponseEntity<AuthenticationResponse> loginPatient(@RequestBody PatientLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/login/doctor")
    public ResponseEntity<AuthenticationResponse> loginDoctor(@RequestBody DoctorLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()) // Assuming Doctor uses email for login identifier
        );
        // UserDetailsServiceImpl handles doctor lookup by email (or username if it were provided)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail()); 
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // This check might be redundant if Spring Security already blocks unauthenticated access to this endpoint based on config
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user or authentication is anonymous.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return ResponseEntity.ok(userDetails.getUsername());
        } else {
            // Handle cases where principal might not be UserDetails (e.g. if some custom auth sets it differently)
            return ResponseEntity.ok(principal.toString());
        }
    }
}
