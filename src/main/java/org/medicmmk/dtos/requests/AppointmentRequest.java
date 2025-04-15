package org.medicmmk.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentRequest {
    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentStartTime;
    private LocalDateTime appointmentEndTime;
}
