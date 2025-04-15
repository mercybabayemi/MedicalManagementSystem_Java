package org.medicmmk.data.repository;

import org.medicmmk.data.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
    Report findReportByPatientId(String patientId);
    Report findReportByDoctorId(String doctorId);
}
