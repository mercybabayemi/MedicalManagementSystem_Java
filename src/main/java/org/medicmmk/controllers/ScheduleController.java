package org.medicmmk.controllers;

import org.medicmmk.data.models.Schedule;
import org.medicmmk.data.models.TimeSlot;
import org.medicmmk.dtos.requests.ScheduleRequest;
import org.medicmmk.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{doctorId}")
    public ResponseEntity<Schedule> createOrUpdateSchedule(
            @PathVariable String doctorId,
            @RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.createOrUpdateSchedule(doctorId, request);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Schedule> viewSchedule(@PathVariable String doctorId) {
        Schedule schedule = scheduleService.viewSchedule(doctorId);
        return ResponseEntity.ok(schedule);
   }

    @PostMapping("/{doctorId}/book/{startTime}")
    public ResponseEntity<String> bookTimeSlot(
            @PathVariable String doctorId,
            @PathVariable TimeSlot time) {
        boolean success = scheduleService.bookTimeSlot(doctorId, time);
        if (success) {
            return ResponseEntity.ok("Time slot booked successfully.");
        } else {
           return ResponseEntity.badRequest().body("Failed to book time slot.");
        }
    }

    @PostMapping("/{doctorId}/cancel/{startTime}")
    public ResponseEntity<String> cancelTimeSlot(
            @PathVariable String doctorId,
            @PathVariable TimeSlot time) {
        boolean success = scheduleService.cancelTimeSlot(doctorId, time);
        if (success) {
            return ResponseEntity.ok("Time slot canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to cancel time slot.");
        }
    }
}
