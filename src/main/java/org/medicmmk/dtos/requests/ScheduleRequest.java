package org.medicmmk.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.medicmmk.data.models.TimeSlot;

import java.util.List;

@Setter
@Getter
public class ScheduleRequest {
    @NotNull(message = "Time slots cannot be null")
    private List<TimeSlot> timeSlots;
}
