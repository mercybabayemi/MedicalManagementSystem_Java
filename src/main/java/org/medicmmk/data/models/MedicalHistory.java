package org.medicmmk.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.medicmmk.data.models.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Document(collection = "medical_history")
public class MedicalHistory {
    @Id
    private String id;
    private Doctor doctor;
    private Patient patient;
    private ArrayList<String> allergies;
    private BloodGroup bloodGroup;
    private Genotype genotype;
    private LocalDateTime lastUpdatedTime;
    private boolean isSurgery;
}
