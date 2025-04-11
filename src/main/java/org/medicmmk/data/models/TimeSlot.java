package org.medicmmk.data.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TimeSlot {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private boolean isBooked;

    public TimeSlot(LocalDateTime newStartTime, LocalDateTime newEndTime) {
        this.startTime = newStartTime;
        this.endTime = newEndTime;
        this.isBooked = false;
    }

    public boolean isAvailable() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }
}
