package org.medicmmk.services;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.dtos.requests.AppointmentRequest;

import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(AppointmentRequest request);
    List<Appointment> viewAppointmentsByDoctorId(String doctorId);
    List<Appointment> viewAppointmentByPatientId(String patientId);
    Appointment viewAppointmentById(String appointmentId);
    Appointment updateAppointment(String appointmentId, AppointmentRequest request);
    void deleteAppointment(String appointmentId);
}
