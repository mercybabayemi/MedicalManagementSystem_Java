package org.medicmmk.services;

import org.medicmmk.data.models.Appointment;
import org.medicmmk.data.models.Report;
import org.medicmmk.data.repository.AppointmentRepository;
import org.medicmmk.data.repository.DoctorRepository;
import org.medicmmk.data.repository.PatientRepository;
import org.medicmmk.data.repository.ReportRepository;
import org.medicmmk.dtos.AppointmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public AppointmentReport generateAppointmentReport(String patientId, String doctorId, String drugRecommendation, String note) {
        List<Appointment> appointments = appointmentRepository.findByPatientIdAndDoctorId(patientId, doctorId);

        if (appointments.isEmpty()) {
            throw new RuntimeException("No appointments found for the given patient and doctor.");
        }

        AppointmentReport report = new AppointmentReport();
        report.setPatientId(patientId);
        report.setDoctorId(doctorId);

        List<AppointmentReport.AppointmentDetail> details = appointments.stream()
                .map(appointment -> {
                    AppointmentReport.AppointmentDetail detail = new AppointmentReport.AppointmentDetail();
                    detail.setAppointmentId(appointment.getId());
                    detail.setAppointmentTime(appointment.getStartTime());
                    detail.setDrugRecommendation(drugRecommendation);
                    detail.setNotes(note);
                    return detail;
                })
                .collect(Collectors.toList());

        report.setAppointmentDetails(details);

        Report reportEntity = new Report();
        reportEntity.setPatientId(patientId);
        reportEntity.setDoctorId(doctorId);
        reportEntity.setGeneratedAt(LocalDateTime.now());
        reportRepository.save(reportEntity);

        return report;
    }
}
