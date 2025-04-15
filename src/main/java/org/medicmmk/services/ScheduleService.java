package org.medicmmk.services;

import org.medicmmk.data.models.Schedule;
import org.medicmmk.data.models.TimeSlot;
import org.medicmmk.dtos.requests.ScheduleRequest;

public interface ScheduleService {
    Schedule createOrUpdateSchedule(String doctorId, ScheduleRequest request);
    Schedule viewSchedule(String doctorId);
    boolean bookTimeSlot(String doctorId, TimeSlot timeSlot);
    boolean cancelTimeSlot(String doctorId, TimeSlot timeSlot);
}
