package org.medicmmk.data.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<TimeSlot> timeSlots;

    public Schedule() {
        this.timeSlots = new ArrayList<>();
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        timeSlots.add(timeSlot);
    }

    public boolean isAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getStartTime().isBefore(endTime) && slot.getEndTime().isAfter(startTime) && !slot.isAvailable()) {
                return false; // Slot is booked
            }
        }
        return true; // Slot is available
    }

    public void bookSlot(LocalDateTime startTime, LocalDateTime endTime) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getStartTime().equals(startTime) && slot.getEndTime().equals(endTime)) {
                slot.book();
                break; // Slot booked
            }
        }
    }
}
