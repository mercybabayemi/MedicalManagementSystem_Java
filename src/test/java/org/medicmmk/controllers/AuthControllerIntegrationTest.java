package org.medicmmk.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medicmmk.data.models.Doctor;
import org.medicmmk.data.models.Patient;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.data.repository.PatientRepository;
import org.medicmmk.dtos.requests.DoctorLoginRequest;
import org.medicmmk.dtos.requests.PatientLoginRequest;
import org.medicmmk.dtos.requests.PatientSignUpRequest;
import org.medicmmk.dtos.requests.RegisterDoctorRequest;
import org.medicmmk.dtos.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Test data placeholders - use unique values for each run or clean up
    private final String patientEmail = "test.patient@example.com";
    private final String patientInitialPassword = "password123";
    private final String doctorInitialEmail = "initial.doctor@example.com";
    private final String doctorInitialUsername = "initialdoctor";
    private final String doctorInitialPassword = "password456";

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
    }

    private PatientSignUpRequest createPatientSignUpRequest(String email, String password, String firstName, String lastName) {
        PatientSignUpRequest signUpRequest = new PatientSignUpRequest();
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        return signUpRequest;
    }

    private RegisterDoctorRequest createDoctorRegisterRequest(String email, String username, String password, String firstName, String lastName, String specialization) {
        RegisterDoctorRequest signUpRequest = new RegisterDoctorRequest();
        signUpRequest.setFirstName(firstName);
        signUpRequest.setLastName(lastName);
        signUpRequest.setEmail(email);
        signUpRequest.setUsername(username);
        signUpRequest.setSpecialization(specialization);
        signUpRequest.setPassword(password);
        return signUpRequest;
    }

    @Test
    void testRegisterPatient_Success() throws Exception {
        PatientSignUpRequest signUpRequest = createPatientSignUpRequest(patientEmail, patientInitialPassword, "Test", "Patient");

        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.signUpResponse").value("SignUp Successful"));

        Patient registeredPatient = patientRepository.findByEmail(patientEmail).orElse(null);
        assertThat(registeredPatient).isNotNull();
        assertThat(registeredPatient.getFirstName()).isEqualTo("Test");
        assertThat(passwordEncoder.matches(patientInitialPassword, registeredPatient.getPassword())).isTrue();
    }

    @Test
    void testRegisterPatient_EmailAlreadyExists() throws Exception {
        // First, register a patient
        PatientSignUpRequest initialRequest = createPatientSignUpRequest(patientEmail, patientInitialPassword, "Test", "Patient");
        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialRequest)))
                .andExpect(status().isOk());

        // Attempt to register another patient with the same email
        PatientSignUpRequest duplicateEmailRequest = createPatientSignUpRequest(patientEmail, "anotherPassword", "Another", "Patient");

        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateEmailRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testRegisterDoctor_Success() throws Exception {
        RegisterDoctorRequest signUpRequest = createDoctorRegisterRequest(doctorInitialEmail, doctorInitialUsername, doctorInitialPassword, "Initial", "Doctor", "Cardiology");

        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Doctor registered successfully"))
                .andExpect(jsonPath("$.id").exists());

        Doctor registeredDoctor = doctorRepository.findByEmail(doctorInitialEmail).orElse(null);
        assertThat(registeredDoctor).isNotNull();
        assertThat(registeredDoctor.getFirstName()).isEqualTo("Initial");
        assertThat(passwordEncoder.matches(doctorInitialPassword, registeredDoctor.getPassword())).isTrue();
    }

    @Test
    void testRegisterDoctor_EmailAlreadyExists_ShouldSucceed() throws Exception {
        // 1. Register initial doctor
        RegisterDoctorRequest initialDoctorRequest = createDoctorRegisterRequest(doctorInitialEmail, doctorInitialUsername, doctorInitialPassword, "Initial", "Doctor", "Cardiology");
        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialDoctorRequest)))
                .andExpect(status().isOk());

        // 2. Attempt to register another doctor with the same email but different username
        RegisterDoctorRequest duplicateEmailRequest = createDoctorRegisterRequest(doctorInitialEmail, "newdocusername", "newPass123", "DuplicateEmail", "Doctor", "Neurology");

        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateEmailRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Doctor registered successfully"))
                .andExpect(jsonPath("$.id").exists());

        Doctor duplicateDoctor = doctorRepository.findByUsername("newdocusername").orElse(null);
        assertThat(duplicateDoctor).isNotNull();
        assertThat(duplicateDoctor.getEmail()).isEqualTo(doctorInitialEmail);
    }

    @Test
    void testRegisterDoctor_UsernameAlreadyExists_ShouldSucceed() throws Exception {
        // 1. Register initial doctor
        RegisterDoctorRequest initialDoctorRequest = createDoctorRegisterRequest(doctorInitialEmail, doctorInitialUsername, doctorInitialPassword, "Initial", "Doctor", "Cardiology");
        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialDoctorRequest)))
                .andExpect(status().isOk());

        // 2. Attempt to register another doctor with the same username but different email
        RegisterDoctorRequest duplicateUsernameRequest = createDoctorRegisterRequest("newdocemail@example.com", doctorInitialUsername, "newPass456", "DuplicateUser", "Doctor", "Pediatrics");

        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateUsernameRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Doctor registered successfully"))
                .andExpect(jsonPath("$.id").exists());

        Doctor duplicateDoctor = doctorRepository.findByEmail("newdocemail@example.com").orElse(null);
        assertThat(duplicateDoctor).isNotNull();
        assertThat(duplicateDoctor.getUsername()).isEqualTo(doctorInitialUsername);
    }

    @Test
    void testLoginPatient_Success() throws Exception {
        // Register patient first using the helper method
        PatientSignUpRequest patientRequest = createPatientSignUpRequest(patientEmail, patientInitialPassword, "Login", "TestPatient");
        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isOk());

        PatientLoginRequest loginRequest = new PatientLoginRequest();
        loginRequest.setEmail(patientEmail);
        loginRequest.setPassword(patientInitialPassword);

        mockMvc.perform(post("/api/v1/auth/login/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void testLoginPatient_BadCredentials() throws Exception {
        // Register patient first
        PatientSignUpRequest patientRequest = createPatientSignUpRequest(patientEmail, patientInitialPassword, "Login", "TestPatient");
        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isOk());

        PatientLoginRequest loginRequest = new PatientLoginRequest();
        loginRequest.setEmail(patientEmail);
        loginRequest.setPassword("wrongPassword");

        mockMvc.perform(post("/api/v1/auth/login/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginDoctor_Success() throws Exception {
        // Register doctor first using the helper
        RegisterDoctorRequest doctorRequest = createDoctorRegisterRequest(doctorInitialEmail, doctorInitialUsername, doctorInitialPassword, "Login", "TestDoctor", "General");
        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorRequest)))
                .andExpect(status().isOk());

        DoctorLoginRequest loginRequest = new DoctorLoginRequest();
        loginRequest.setEmail(doctorInitialEmail);
        loginRequest.setPassword(doctorInitialPassword);

        mockMvc.perform(post("/api/v1/auth/login/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    void testAccessProtectedEndpoint_Unauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccessProtectedEndpoint_AsPatient() throws Exception {
        PatientSignUpRequest patientRequest = createPatientSignUpRequest(patientEmail, patientInitialPassword, "ProtectedAccess", "Patient");
        mockMvc.perform(post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isOk());

        PatientLoginRequest loginRequest = new PatientLoginRequest();
        loginRequest.setEmail(patientEmail);
        loginRequest.setPassword(patientInitialPassword);

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = loginResult.getResponse().getContentAsString();
        AuthenticationResponse authResponse = objectMapper.readValue(responseString, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();

        mockMvc.perform(get("/api/v1/auth/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(patientEmail));
    }

    @Test
    void testAccessProtectedEndpoint_AsDoctor() throws Exception {
        RegisterDoctorRequest doctorRequest = createDoctorRegisterRequest(doctorInitialEmail, doctorInitialUsername, doctorInitialPassword, "ProtectedAccess", "Doctor", "Ortho");
        mockMvc.perform(post("/api/v1/auth/register/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorRequest)))
                .andExpect(status().isOk());

        DoctorLoginRequest loginRequest = new DoctorLoginRequest();
        loginRequest.setEmail(doctorInitialEmail);
        loginRequest.setPassword(doctorInitialPassword);

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = loginResult.getResponse().getContentAsString();
        AuthenticationResponse authResponse = objectMapper.readValue(responseString, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();

        mockMvc.perform(get("/api/v1/auth/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(doctorInitialUsername));
    }
}
