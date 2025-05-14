package org.medicmmk.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSignUpRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

}