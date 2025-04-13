package org.medicmmk.data.models;

import lombok.Data;
import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.MedicalHistory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "patient")
public class Patient {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private String dateOfBirth;
    private String password;
    @DBRef
    private MedicalHistory medicalHistory;
    @DBRef
    private Appointment patientAppointment;
}
