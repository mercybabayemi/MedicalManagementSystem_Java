package org.medicmmk.dtos.requests;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetDoctorProfile {

    private String username;
    private String email;
    private String password;
}
