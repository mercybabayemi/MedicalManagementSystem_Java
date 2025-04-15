package org.medicmmk.data.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "schedule")
public class Schedule {
    @Id
    private String id;
    @NotNull
    private String doctorId;
    private List<TimeSlot> timeSlots;
}
