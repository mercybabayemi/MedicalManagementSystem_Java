package org.medicmmk.controllers;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.dtos.requests.AppointmentRequest;
import org.medicmmk.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.bookAppointment(request);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> viewAppointmentsByDoctorId(@PathVariable String doctorId) {
        List<Appointment> appointments = appointmentService.viewAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> viewAppointmentsByPatientId(@PathVariable String patientId) {
        List<Appointment> appointments = appointmentService.viewAppointmentByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

   @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> viewAppointmentById(@PathVariable String appointmentId) {
        Appointment appointment = appointmentService.viewAppointmentById(appointmentId);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String appointmentId, @RequestBody AppointmentRequest request) {
        Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, request);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
