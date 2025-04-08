package org.medicmmk.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.medicmmk.data.models.Patient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "medical_report")
public class MedicalReport {

    @Id
    private String id;

    private Doctor doctor;
    private Patient patient;
    private MedicalHistory medicalHistory;
}
