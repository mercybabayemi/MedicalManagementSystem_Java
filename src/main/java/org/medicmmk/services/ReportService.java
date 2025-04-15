package org.medicmmk.services;

import org.medicmmk.dtos.AppointmentReport;

public interface ReportService {
    AppointmentReport generateAppointmentReport(String patientId, String doctorId, String drugRecommendation, String note);
}
