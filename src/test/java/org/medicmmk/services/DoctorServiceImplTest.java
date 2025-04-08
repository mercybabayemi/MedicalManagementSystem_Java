package org.medicmmk.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Gender;
import org.medicmmk.data.models.Specialisation;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DoctorServiceImplTest {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp(){
        doctorRepository.deleteAll();
    }

    @Test
    void testRegisterDoctor(){
        Doctor doctor = new Doctor();
        doctor.setFirstName("Adam");
        doctor.setLastName("Eve");
        doctor.setEmail("test@email.com");
        doctor.setPassword("test");
        doctor.setUsername("adameve");
        doctor.setGender(Gender.FEMALE);
        doctor.setOnCall(true);
        doctor.setDateOfBirth("31121988");
        doctor.setSpecialisation(Specialisation.GYNECOLOGIST);

        RegisterDoctorRequest request = new RegisterDoctorRequest();
        request.setDoctor(doctor);

        Doctor registeredDoctor = doctorService.registerDoctorProfile(request);
        assertEquals("adameve", registeredDoctor.getUsername());
        assertTrue(doctorRepository.findByUsername("adameve").isPresent());
    }


    @Test
    void testFindDoctorByUsername(){
        Doctor doctor = new Doctor();
        doctor.setFirstName("Adam");
        doctor.setLastName("Eve");
        doctor.setEmail("test@email.com");
        doctor.setPassword("test");
        doctor.setUsername("adameve");
        doctor.setGender(Gender.FEMALE);
        doctor.setOnCall(true);
        doctor.setDateOfBirth("31121988");
        doctor.setSpecialisation(Specialisation.GYNECOLOGIST);

        RegisterDoctorRequest request = new RegisterDoctorRequest();
        request.setDoctor(doctor);
        doctorService.registerDoctorProfile(request);
        Doctor foundDoctor = doctorService.findDoctorProfileByUsername("adameve");

        assertEquals("adameve", foundDoctor.getUsername());
    }

    @Test
    void testFindDoctorByEmail(){
        Doctor doctor = new Doctor();
        doctor.setFirstName("Adam");
        doctor.setLastName("Eve");
        doctor.setEmail("test@email.com");
        doctor.setPassword("test");
        doctor.setUsername("adameve");
        doctor.setGender(Gender.FEMALE);
        doctor.setOnCall(true);
        doctor.setDateOfBirth("31121988");
        doctor.setSpecialisation(Specialisation.GYNECOLOGIST);

        RegisterDoctorRequest request = new RegisterDoctorRequest();
        request.setDoctor(doctor);
        doctorService.registerDoctorProfile(request);
        Doctor foundDoctor = doctorService.findDoctorProfileByEmail("test@email.com");

        assertEquals("test@email.com", foundDoctor.getEmail());
    }

    @Test
    void testFindDoctorByPassword(){
        Doctor doctor = new Doctor();
        doctor.setFirstName("Adam");
        doctor.setLastName("Eve");
        doctor.setEmail("test@email.com");
        doctor.setPassword("test");
        doctor.setUsername("adameve");
        doctor.setGender(Gender.FEMALE);
        doctor.setOnCall(true);
        doctor.setDateOfBirth("31121988");
        doctor.setSpecialisation(Specialisation.GYNECOLOGIST);

        RegisterDoctorRequest request = new RegisterDoctorRequest();
        request.setDoctor(doctor);
        doctorService.registerDoctorProfile(request);
        Doctor foundDoctor = doctorService.findDoctorProfileByPassword("test");

        assertEquals("test@email.com", foundDoctor.getPassword());
    }
}