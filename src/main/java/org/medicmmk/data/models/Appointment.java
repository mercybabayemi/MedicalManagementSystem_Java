package org.medicmmk.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.medicmmk.data.models.Patient;
import java.time.LocalDateTime;

@Data
@Document(collection = "appointment")
public class Appointment {
    @Id
    private String id;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Patient patient;
    private Doctor doctor;
    private Status status;
}