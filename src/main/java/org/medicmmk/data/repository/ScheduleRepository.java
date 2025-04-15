package org.medicmmk.data.repository;

import org.medicmmk.data.models.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    Schedule findByDoctorId(String doctorId);
}
