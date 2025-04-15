package org.medicmmk.data.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    @NotNull(message = "Patient must be assigned")
    private String patientId;
    @DBRef
    @NotNull(message = "Doctor must be assigned")
    private String doctorId;
    private Status status;
}