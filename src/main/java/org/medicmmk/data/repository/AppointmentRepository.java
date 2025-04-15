package org.medicmmk.data.repository;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findAppointmentsByDoctorId(String doctorId);
    List<Appointment> findAppointmentByPatientId(String patientId);

}
