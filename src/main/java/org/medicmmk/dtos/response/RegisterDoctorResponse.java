package org.medicmmk.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDoctorResponse {

    private String id;
    private String message;

    public RegisterDoctorResponse(String newId,String  newMessage){
        this.id = newId;
        this.message = newMessage;
    }
}
