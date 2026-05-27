package com.example.notesAPI.dto.Label;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateLabelDTO {

    @Schema(name = "label", example = "sample label", requiredMode = Schema.RequiredMode.REQUIRED)
    private String label;

    @JsonIgnore
    public boolean isValid() {
        if (label == null || label.isBlank()) {
            return false;
        }
        return true;
    }
}
