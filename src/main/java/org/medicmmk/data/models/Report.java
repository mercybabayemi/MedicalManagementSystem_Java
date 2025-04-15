package org.medicmmk.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document(collection = "report")
public class Report {
    @Id
    private Long id;

    private String patientId;
    private String doctorId;
    private LocalDateTime generatedAt;
}
