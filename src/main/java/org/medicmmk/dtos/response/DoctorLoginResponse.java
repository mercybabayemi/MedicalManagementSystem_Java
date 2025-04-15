package org.medicmmk.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorLoginResponse {
    private String id;
    private String message;

    public DoctorLoginResponse(String newId,String  newMessage){
        this.id = newId;
        this.message = newMessage;
    }
}
