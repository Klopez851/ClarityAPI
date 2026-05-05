package com.example.notesAPI.dto.Label;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateBooleanStatusDTO {
    @Schema(name = "email",example = "sampleemail@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(name = "noteID",example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int noteID;

    @Schema(name = "newValue",example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean newValue;

    @JsonIgnore
    public boolean isValid(){
        if(email == null || email.isBlank() || noteID<0){
            return false;
        }
        return true;
    }
}
