package org.medicmmk.data.repository;

import org.medicmmk.data.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
     Patient findByEmail(String email);
}