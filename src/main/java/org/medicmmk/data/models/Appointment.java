package org.medicmmk.data.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "appointment")
public class Appointment {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotNull(message = "Patient must be assigned")
    private Patient patient;
    @NotNull(message = "Doctor must be assigned")
    private Doctor doctor;
    private Status status;
}