package org.medicmmk.data.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "doctor")
public class Doctor {
    @Id
    private String id;
    @NotNull(message = "Username cannot be null")
    private String username;
    private String password;
    @NotBlank(message = "Name cannot be blank")
    private String firstName;
    @NotBlank(message = "Name cannot be blank")
    private String lastName;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    private String phone;
    private Gender gender;
    @Past(message = "Date of birth must be in the past")
    private String dateOfBirth;
    private Specialisation specialisation;
    private boolean isOnCall;
    private Schedule schedule;
}
