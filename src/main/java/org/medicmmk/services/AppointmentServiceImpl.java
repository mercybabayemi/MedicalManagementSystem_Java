package org.medicmmk.services;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Schedule;
import org.medicmmk.data.models.Status;
import org.medicmmk.data.repository.AppointmentRepository;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.data.repository.ScheduleRepository;
import org.medicmmk.dtos.requests.AppointmentRequest;
import org.medicmmk.exceptions.AppointmentNotFoundException;
import org.medicmmk.exceptions.DoctorNotFoundException;
import org.medicmmk.exceptions.ScheduleNotFoundException;
import org.medicmmk.exceptions.TimeSlotUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AppointmentServiceImpl  implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Appointment bookAppointment(AppointmentRequest request) {
        Schedule schedule = scheduleRepository.findByDoctorId(request.getDoctorId());
        if (schedule == null) {
            throw new DoctorNotFoundException("Doctor's schedule not found.");
        }
        boolean isTimeAvailable = schedule.getTimeSlots().stream()
                .anyMatch(slot -> slot.getStartTime().equals(request.getAppointmentStartTime()) && !slot.isBooked());
        if(!isTimeAvailable){
            throw new TimeSlotUnavailableException("The selected time slot is not available.");
        }
        Appointment appointment = new Appointment();
        appointment.setDoctorId(request.getDoctorId());
        appointment.setPatientId(request.getPatientId());
        appointment.setStartTime(request.getAppointmentStartTime());
        appointment.setEndTime(request.getAppointmentStartTime());
        appointment.setStatus(Status.PENDING);

        schedule.getTimeSlots().stream()
                .filter(slot -> slot.getStartTime().equals(request.getAppointmentStartTime()))
                .forEach(slot -> slot.setBooked(true));

        scheduleRepository.save(schedule);

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> viewAppointmentsByDoctorId(String doctorId) {
            return appointmentRepository.findAppointmentsByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> viewAppointmentByPatientId(String patientId) {
        return appointmentRepository.findAppointmentByPatientId(patientId);
    }

    @Override
    public Appointment viewAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public Appointment updateAppointment(String appointmentId, AppointmentRequest request) {
        Optional<Appointment> existingAppointmentOpt = appointmentRepository.findById(appointmentId);
        if (existingAppointmentOpt.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment not found.");
        }
        Appointment existingAppointment = existingAppointmentOpt.get();

        Schedule schedule = scheduleRepository.findByDoctorId(request.getDoctorId());
        if (schedule == null) {
            throw new ScheduleNotFoundException("Doctor's schedule not found.");
        }

        boolean isTimeAvailable = schedule.getTimeSlots().stream()
                .anyMatch(slot -> slot.getStartTime().equals(request.getAppointmentStartTime()) && !slot.isBooked());

        if (!isTimeAvailable) {
            throw new TimeSlotUnavailableException("The selected time slot is not available.");
        }

        existingAppointment.setStartTime(request.getAppointmentStartTime());
        existingAppointment.setEndTime(request.getAppointmentEndTime());
        existingAppointment.setDoctorId(request.getDoctorId());
        existingAppointment.setPatientId(request.getPatientId());

        Schedule oldSchedule = scheduleRepository.findByDoctorId(existingAppointment.getDoctorId());
        oldSchedule.getTimeSlots().stream()
                .filter(slot -> slot.getStartTime().equals(existingAppointment.getStartTime()))
                .forEach(slot -> slot.setBooked(false));

        schedule.getTimeSlots().stream()
                .filter(slot -> slot.getStartTime().equals(request.getAppointmentStartTime()))
                .forEach(slot -> slot.setBooked(true));

        scheduleRepository.save(oldSchedule);
        scheduleRepository.save(schedule);

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
