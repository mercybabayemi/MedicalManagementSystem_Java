package org.medicmmk.data.repository;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

}
