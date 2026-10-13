package com.example.notesAPI.dto.noteColor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateNoteColorDTO {

    @Schema(name = "colorHex", example = "#b5a2c8", requiredMode = Schema.RequiredMode.REQUIRED)
    private String colorHex;

    @JsonIgnore
    public boolean isValid() {
        if (colorHex == null || colorHex.isBlank()) {
            return false;
        }
        return true;
    }

}
