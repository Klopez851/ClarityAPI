package com.example.notesAPI.dto.noteColor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"colorHEX", "colorID"})
public class NoteColorDTO {
    @Schema(name = "colorID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int colorID;

    @Schema(name = "colorHEX", example = "#b5a2c8", requiredMode = Schema.RequiredMode.REQUIRED)
    private String colorHEX;

    //this constructor matches the exact order the query NoteColorRepository.getAllNoteByUser() requires
    // changes made here need to be reflected there.
    public NoteColorDTO(String colorHex, int colorID) {
        this.colorID = colorID;
        this.colorHEX = colorHex;
    }
}
