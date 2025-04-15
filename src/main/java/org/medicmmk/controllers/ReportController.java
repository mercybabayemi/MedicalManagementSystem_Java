package org.medicmmk.controllers;

import org.medicmmk.dtos.AppointmentReport;
import org.medicmmk.dtos.requests.AppointmentReportRequest;
import org.medicmmk.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/appointments")
    public AppointmentReport getAppointmentReport(@RequestBody AppointmentReportRequest request) {
        return reportService.generateAppointmentReport(request.getPatientId(), request.getDoctorId(), request.getDrugRecommendation(), request.getNote());
    }
}
