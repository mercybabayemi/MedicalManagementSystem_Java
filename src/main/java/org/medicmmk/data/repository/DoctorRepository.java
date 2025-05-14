package org.medicmmk.data.repository;

import org.medicmmk.data.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUsername(String username);
}
