package org.medicmmk.services;

import lombok.RequiredArgsConstructor;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Patient;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.data.repository.PatientRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Try to load as a Patient by email
        Patient patient = patientRepository.findByEmail(usernameOrEmail);
        if (patient != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
            return new User(patient.getEmail(), patient.getPassword(), authorities);
        }

        // Try to load as a Doctor by username
        Optional<Doctor> doctorOptional = doctorRepository.findByUsername(usernameOrEmail);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
            return new User(doctor.getUsername(), doctor.getPassword(), authorities);
        }

        // Try to load as a Doctor by email (if not found by username)
        Optional<Doctor> doctorByEmailOptional = doctorRepository.findByEmail(usernameOrEmail);
        if (doctorByEmailOptional.isPresent()) {
            Doctor doctor = doctorByEmailOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
            return new User(doctor.getEmail(), doctor.getPassword(), authorities); // Use email as principal
        }

        throw new UsernameNotFoundException("User not found with identifier: " + usernameOrEmail);
    }
}
