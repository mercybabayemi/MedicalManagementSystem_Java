package org.medicmmk.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppointmentReport {
    private String patientId;
    private String doctorId;
    private List<AppointmentDetail> appointmentDetails;

    @Data
    public static class AppointmentDetail {
        private String appointmentId;
        private LocalDateTime appointmentTime;
        private String drugRecommendation;
        private String notes;
    }
}
