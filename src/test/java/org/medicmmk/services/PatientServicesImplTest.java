package org.medicmmk.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medicmmk.exceptions.DuplicatePatientException;
import org.medicmmk.exceptions.InvalidPasswordException;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.response.PatientLoginResponse;
import org.medicmmk.dtos.response.PatientSignUpResponse;
import org.medicmmk.exceptions.PatientDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServicesImplTest {

    PatientSignUpRequest patientSignUpRequest;
    @Autowired
    private PatientServicesImpl patientServiceImpl;
    @Autowired
    private MedicalReportServicesImpl medicalReportServiceImpl;

    @BeforeEach
    public void setUp() {
        patientServiceImpl.deleteAllPatients();
        patientSignUpRequest = new PatientSignUpRequest();
        patientSignUpRequest.setFirstName("FirstName");
        patientSignUpRequest.setLastName("LastName");
        patientSignUpRequest.setUserName("UserName");
        patientSignUpRequest.setEmail("email@email.com");
        patientSignUpRequest.setPassword("password");
    }

    @Test
    public void PatientCanSignUpTest(){
        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        assertEquals(1,patientServiceImpl.patientsCount());
    }

    @Test
    public void NoDuplicatePatientCanSignUpTest() {
        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        assertEquals(1,patientServiceImpl.patientsCount());
        PatientSignUpRequest patientSignUpRequestDuplicate = new PatientSignUpRequest();
        patientSignUpRequestDuplicate.setFirstName("FirstName");
        patientSignUpRequestDuplicate.setLastName("LastName");
        patientSignUpRequestDuplicate.setUserName("UserName");
        patientSignUpRequestDuplicate.setEmail("email@email.com");
        patientSignUpRequestDuplicate.setPassword("password");
        assertEquals(1,patientServiceImpl.patientsCount());
        assertThrows( DuplicatePatientException.class , ()-> patientServiceImpl.signUp(patientSignUpRequestDuplicate));
        assertEquals(1,patientServiceImpl.patientsCount());
    }


    @Test
    public void MoreThanOnePatientCanSIgnUpTest(){

        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        assertEquals(1,patientServiceImpl.patientsCount());
        PatientSignUpRequest patientSignUpRequestTwo = new PatientSignUpRequest();
        patientSignUpRequestTwo.setFirstName("SecondFirstName");
        patientSignUpRequestTwo.setLastName("SecondLastName");
        patientSignUpRequestTwo.setUserName("UserName");
        patientSignUpRequestTwo.setEmail("SecondEmail@email.com");
        patientSignUpRequestTwo.setPassword("SecondPassword");
        PatientSignUpResponse patientSignUpResponseTwo = patientServiceImpl.signUp(patientSignUpRequestTwo);
        assertThat(patientSignUpResponseTwo, notNullValue());
        assertEquals(2,patientServiceImpl.patientsCount());

    }
    @Test
    public void A_PatientCanLoginTest(){


        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        PatientLoginRequest patientLoginRequest = new PatientLoginRequest();
        patientLoginRequest.setEmail("email@email.com");
        patientLoginRequest.setPassword("password");
        PatientLoginResponse patientLoginResponse = patientServiceImpl.login(patientLoginRequest);
        assertThat(patientLoginResponse, notNullValue());

    }

    @Test
    public void PatientCanNotLoginWithInvalidUserPassword_Test(){

        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        PatientLoginRequest patientLoginRequest = new PatientLoginRequest();
        patientLoginRequest.setEmail("email@email.com");
        patientLoginRequest.setPassword("WrongPassword");
        assertThrows( InvalidPasswordException.class,()->patientServiceImpl.login(patientLoginRequest));

    }

    @Test
    public void PatientCanNotLoginWithInvalidEmailTest(){

        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        PatientLoginRequest patientLoginRequest = new PatientLoginRequest();
        patientLoginRequest.setEmail("wrongemail@email.com");
        patientLoginRequest.setPassword("Password");
        assertThrows( PatientDoesNotExistException.class,()->patientServiceImpl.login(patientLoginRequest));

    }
    @Test
    public void PatientCanBookAppointmentTest(){
        PatientSignUpResponse patientSignUpResponse = patientServiceImpl.signUp(patientSignUpRequest);
        assertThat(patientSignUpResponse, notNullValue());
        PatientLoginRequest patientLoginRequest = new PatientLoginRequest();
        patientLoginRequest.setEmail("email@email.com");
        patientLoginRequest.setPassword("Password");
        patientServiceImpl.login(patientLoginRequest);
        PatientAppointmentRequest patientAppointmentRequest = new PatientAppointmentRequest();
//        patientAppointmentRequest.setDate();
//        patientAppointmentRequest.getDoctor();
        patientServiceImpl.bookAppointment(patientAppointmentRequest);


    }


}