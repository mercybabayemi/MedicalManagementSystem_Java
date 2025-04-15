package org.medicmmk.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentReportRequest {
    private String patientId;
    private String doctorId;
    private String note;
    private String drugRecommendation;
}
