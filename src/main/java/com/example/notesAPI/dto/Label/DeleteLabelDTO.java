package com.example.notesAPI.dto.Label;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteLabelDTO {

    @Schema(name = "labelID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String labelID;

    @JsonIgnore
    public boolean isValid() {
        if (labelID == null || labelID.isBlank()) {
            return false;
        }
        return true;
    }

}
