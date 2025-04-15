package org.medicmmk.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;
}
